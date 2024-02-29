package com.school.teacher.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.teacher.datamodel.Section;
import com.school.teacher.datastate.DataNode;
import com.school.teacher.datastate.DataState;
import com.school.teacher.exception.ResourceNotFoundException;
import com.school.teacher.repository.DataStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class DataStateService {

    private Logger logger = LoggerFactory.getLogger(DataStateService.class);

    @Autowired
    private DataCaptureConfig dataCaptureConfig;

    @Autowired
    private DataStateRepository dataStateRepository;

    private ObjectMapper mapper = new ObjectMapper();

    public DataNode getDataState(String type, String id, String context) throws IOException {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(id) || StringUtils.isEmpty(context)) {
            throw new ResourceNotFoundException("Error fetching DataState for Type - %s, Reference - %s, Context - %s", type, id);
        }
        DataState state = dataStateRepository.findByDataTypeAndDataReferenceAndContext(type, id, context);
        if (state != null && !StringUtils.isEmpty(state.getRoot())) {
            DataNode dataNode = mapper.readValue(state.getRoot(), DataNode.class);
            dataNode.setHierarchy();
            return dataNode;
        }
        return new DataNode(type, id);
    }

    public DataNode saveDataState(String context, DataNode dataNode) throws JsonProcessingException {
        DataState dataState
                = dataStateRepository.findByDataTypeAndDataReferenceAndContext(dataNode.getType(),
                dataNode.getReference(), context);
        if (dataState == null) {
            dataState = new DataState(dataNode.getType(), dataNode.getReference(), context);
        }
        dataState.setRoot(mapper.writeValueAsString(dataNode));
        dataStateRepository.save(dataState);
        return dataNode;
    }

    public boolean isSectionComplete(String context, String reference, String sectionRef, String... path) {
        try {
            DataNode dataNode = getDataState(path[0], reference, context);
            DataNode child = dataNode.findChild(sectionRef, path);
            if (child == null) {
                logger.error("Section path reference not found {}", StringUtils.arrayToDelimitedString(path, "->"));
                throw new RuntimeException("Failed to fetch section status");
            }
            return child.getStatus() == DataNode.Status.COMPLETED;
        } catch (IOException e) {
            logger.error("Section validation failed", e);
            throw new RuntimeException("Section validation failed", e);
        }
    }

    public void setSectionStatus(DataNode.Status status, String context, String rootReference, String sectionRef, String... path) {
        try {
            DataNode dataNode = getDataState(path[0], rootReference, context);
            DataNode child = dataNode.findOrCreateChild(sectionRef, path);
            if (child == null) {
                logger.error("Section path reference not found {}", StringUtils.arrayToDelimitedString(path, "->"));
                throw new RuntimeException("Failed to update section status");
            }
            child.setStatus(status);
            saveDataState(context, dataNode);
            logger.info("DataState for {}-{} sectionRef - {} path - {} saved - updated section - {}",
                    context, rootReference, sectionRef, path, child);
        } catch (IOException e) {
            logger.error("Failed to set section status", e);
            throw new RuntimeException("Failed to update section status", e);
        }
    }

    public void setSectionStatus(DataNode.Status status, String context, String[] sectionRef, String[] path) {
        try {
            DataNode dataNode = getDataState(path[0], sectionRef[0], context);
            DataNode child = dataNode.findOrCreateChild(sectionRef, path);
            if (child == null) {
                logger.error("Section path reference not found {}", StringUtils.arrayToDelimitedString(path, "->"));
                throw new RuntimeException("Failed to update section status");
            }
            child.setStatus(status);
            saveDataState(context, dataNode);
            logger.info("DataState for {} sectionRef - {} path - {} saved - updated section - {}",
                    context, sectionRef, path, child);
        } catch (IOException e) {
            logger.error("Failed to set section status", e);
            throw new RuntimeException("Failed to update section status", e);
        }
    }


    /**
     * Sets the status of sections (creating the path if missing) to the given value
     * Path elements are expected to be of the form &lt;&lt;type&gt;&gt;::&lt;&lt;reference&gt;&gt;.
     *
     * @param status
     * @param context
     * @param graphPath
     */
    public void setSectionStatus(DataNode.Status status, String context, DataNode.Path graphPath) {
        try {
            DataNode dataNode = getDataState(graphPath.getType()[0], graphPath.getReference()[0], context);
            DataNode child = dataNode.findOrCreateChild(graphPath);
            if (child == null) {
                logger.error("Section path reference not found {}", StringUtils.arrayToDelimitedString(graphPath.getType(), "->"));
                throw new RuntimeException("Failed to update section status");
            }
            child.setStatus(status);
            saveDataState(context, dataNode);
            logger.info("DataState for {} path - {} saved - updated section - {}",
                    context, graphPath, child);
        } catch (IOException e) {
            logger.error("Failed to set section status", e);
            throw new RuntimeException("Failed to update section status", e);
        }
    }

    public void deleteSection(String context, String rootReference, String sectionRef, String... path) {
        try {
            DataNode dataNode = getDataState(path[0], rootReference, context);
            final DataNode deletedNode = dataNode.deleteNode(sectionRef, path);
            logger.info("Deleted node => context - {}, reference - {}, section - {}, path - {}, Node - {}",
                    context, rootReference, sectionRef, path, deletedNode);
            saveDataState(context, dataNode);
        } catch (IOException e) {
            logger.error("Failed to set section status", e);
            throw new RuntimeException("Failed to update section status", e);
        }
    }

//    ---------------------------------

    public List<String> getIncompleteSections(String context, String reference, String... path) {
        try {
            if (dataCaptureConfig != null && dataCaptureConfig.getSections() != null) {
                DataNode dataNode = getDataState(path[0], reference, context);
                logger.info("DataNode for Ref: {} - {}", dataNode);
                Section section = findSection(path[0], dataCaptureConfig.getSections());
                if (section != null) {
                    List<String> incompleteSections = new ArrayList<>();
                    checkMandatoryData(section, dataNode, dataNode.getParent(), incompleteSections, null);
                    logger.error("Mandatory sections incomplete - {}", incompleteSections);
                    return incompleteSections;
                }
            }
        } catch (IOException e) {
            logger.error("Could not fetch the data state for context - {}, type - {}, reference - {}"
                    , context, reference, path[0]);
            throw new RuntimeException(e);
        }
        return null;
    }

    private Section findSection(String sectionName, List<Section> sections) {
        for (Section section : sections) {
            if (sectionName.equals(section.getName())) {
                return section;
            }
        }
        return null;
    }

    private void checkMandatoryData(Section section, DataNode dataNode, DataNode parent, List<String> incompleteSections, String parentPath)
    {

        if (parentPath == null) {
            parentPath = "";
        }
        if (section.isValidationDisabled()) {
            return;
        }
//        if ((userAuthorities == null || contains(userAuthorities, section.getMandatory()))
//                && section.isLeafSection()) {
//            logger.info("User Authorities - {}, Section - {}", userAuthorities == null ? "NONE" : String.join(", ", userAuthorities), section);
//            if (dataNode == null || dataNode.getStatus() != DataNode.Status.COMPLETED) {
//                if (dataNode != null) {
//                    logger.warn("Section {} under {} >> {} ({}) is {}", section.getName(), parentPath, dataNode.getType(), dataNode.getReference(), dataNode.getStatus());
//                } else {
//                    logger.warn("DataNode not found for path {} >> {}", parentPath, section.getName());
//                }
//                incompleteSections.add(evalMessageString(section, dataNode, parent));
//            }
//        }
        if (!section.isLeafSection() && (dataNode == null || dataNode.getStatus() != DataNode.Status.COMPLETED)) {
            for (Section childSection : section.getSections()) {
                Set<DataNode> children = null;
                String sectionPath = parentPath + " >> " + childSection.getDisplayName();
                if (dataNode != null) {
                    children = dataNode.findChildren(childSection.getName());
                    for (DataNode child : children) {
                        checkMandatoryData(childSection, child, dataNode, incompleteSections, sectionPath);
                    }
                }
                if (children == null || children.isEmpty()) {
                    checkMandatoryData(childSection, null, parent, incompleteSections, sectionPath);
                }
            }
        }
    }

}
