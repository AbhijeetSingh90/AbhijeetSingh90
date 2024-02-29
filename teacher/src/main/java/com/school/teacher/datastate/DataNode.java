package com.school.teacher.datastate;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DataNode implements Comparable<DataNode>{

    @JsonBackReference
    DataNode parent;
    private String type;
    private String reference;
    private Status status;
    @JsonProperty("children")
    private Set<DataNode> children;

    public DataNode() {
        this.status = Status.NOT_STARTED;
        children = new HashSet<>();
    }

    public DataNode(DataNode parent) {
        this();
        this.parent = parent;
    }

    public DataNode(String type, String reference) {
        this();
        this.type = type;
        this.reference = reference;
    }

    public void setHierarchy() {
        boolean isComplete = true;
        for (DataNode child : children) {
            child.parent = this;
            child.setHierarchy();
            isComplete &= child.status == Status.COMPLETED;
        }
    }

    public String getType() {
        return type;
    }

    public DataNode setType(String type) {
        this.type = type;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public DataNode setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public DataNode setStatus(Status status) {
        if(status == Status.DELETED) {
            if(this.parent != null) {
                return this.parent.deleteNode(this);
            }
        } else {
            this.status = status;
            if (this.parent != null) {
                this.parent.childStatusChanged(this);
            }
        }
        return this;
    }

    public DataNode getParent() {
        return parent;
    }

    private void childStatusChanged(DataNode child) {
        boolean complete = true;
        //check the status of all children and update the node's status
        for (DataNode dataNode : this.children) {
            if (dataNode.getStatus() != Status.COMPLETED) {
                complete = false;
                break;
            }
        }
        if (complete) {
            this.setStatus(Status.COMPLETED);
        } else {
            this.setStatus(Status.IN_PROGRESS);
        }
    }

    public DataNode addChild(DataNode child) {
        child.parent = this;
        childStatusChanged(child);
        children.add(child);
        return child;
    }

    /**
     * Returns the node which matches with the reference at the given typePath
     *
     * @param reference
     * @param typePath
     * @return
     */
    public DataNode findChild(String reference, String... typePath) {
        if (typePath.length > 0) {
            if (typePath[0].equals(this.getType()) && typePath.length == 1
                    && (reference == null || Objects.equals(reference, this.reference))) {
                return this;
            } else {
                for (DataNode child : this.children) {
                    if (Objects.equals(child.type, typePath[0]) && typePath.length == 1
                            && (child.getReference() == null || Objects.equals(reference, child.getReference()))) {
                        return child;
                    } else {
                        //depth search
                        DataNode childNode = child.findChild(reference,
                                Arrays.copyOfRange(typePath, 1, typePath.length));
                        if (childNode != null) {
                            return childNode;
                        }
                    }
                }
            }
        }
        /*if (foundChild != null && typePath.length > 1) {
            return foundChild.findChild(reference,
                    Arrays.copyOfRange(typePath, 1, typePath.length));
        }*/
        return null;
    }

    public Set<DataNode> findChildren(String type) {
        Set<DataNode> childrenSet = new HashSet<>();
        if (this.children != null) {
            for (DataNode child : children) {
                if (child.getType().equals(type)) {
                    childrenSet.add(child);
                }
            }
        }
        return childrenSet;
    }

    public DataNode findOrCreateChild(String reference, String... typePath) {
        DataNode foundChild = null;
        if (typePath.length > 0) {
            if (Objects.equals(this.type, typePath[0])
                    && typePath.length > 1) {
                //when there is still depth in the path, dont try to match reference.
                //Reference should be checked only at the last level
                foundChild = this;
            }
            if (foundChild == null) {
                for (DataNode child : this.children) {
                    if (Objects.equals(child.type, typePath[0])
                            && (child.getReference() == null || Objects.equals(reference, child.getReference()))) {
                        foundChild = child;
                        break;
                    }
                }
            }

            if (foundChild == null) {
                foundChild = addChild(new DataNode())
                        .setType(typePath[0]).setReference(typePath.length == 1 ? reference : null);
            }
        }
        if (foundChild != null && typePath.length > 1) {
            return foundChild.findOrCreateChild(reference,
                    Arrays.copyOfRange(typePath, 1, typePath.length));
        }
        return foundChild;
    }

    public DataNode findOrCreateChild(Path path) {
        return findOrCreateChild(path.reference, path.type);
    }

    public DataNode findChild(Path graphPath) {
        return findChild(graphPath.getReference(), graphPath.getType());
    }

    public DataNode findChild(String[] reference, String[] typePath) {
        DataNode foundChild = null;
        if (typePath.length > 0 && reference.length > 0) {
            if (typePath[0].equals(this.getType())) {
                if (Objects.equals(reference[0], this.getReference())) {
                    foundChild = this;
                }
            } else {
                for (DataNode child : this.children) {
                    if (Objects.equals(reference[0], child.getReference())) {
                        foundChild = child;
                        break;
                    }
                }
            }
        }
        if (foundChild != null && typePath.length > 1) {
            return foundChild.findChild(Arrays.copyOfRange(reference, 1, reference.length),
                    Arrays.copyOfRange(typePath, 1, typePath.length));
        }
        return foundChild;
    }

    public DataNode findOrCreateChild(String[] reference, String[] typePath) {
        DataNode foundChild = null;
        if (typePath.length > 0 && reference.length > 0) {
            if (Objects.equals(typePath[0], this.getType())
                    && Objects.equals(reference[0], this.getReference())) {
                if (Objects.equals(reference[0], this.getReference())) {
                    foundChild = this;
                }
            } else {
                for (DataNode child : this.children) {
                    if (Objects.equals(typePath[0], child.getType())
                            && Objects.equals(reference[0], child.getReference())) {
                        foundChild = child;
                        break;
                    }
                }
                if (foundChild == null) {
                    foundChild = addChild(new DataNode())
                            .setType(typePath[0]).setReference(reference[0]);
                }
            }
        }
        if (foundChild != null && typePath.length > 1) {
            return foundChild.findOrCreateChild(Arrays.copyOfRange(reference, 1, reference.length),
                    Arrays.copyOfRange(typePath, 1, typePath.length));
        }
        return foundChild;
    }

    public DataNode deleteNode(String reference, String... typePath) {
        DataNode child = findChild(reference, typePath);
        return deleteNode(child);
    }

    public DataNode deleteNode(DataNode child) {
        if (child != null) {
            DataNode parent = child.parent;
            if (parent != null) {
                parent.children.remove(child);
                parent.childStatusChanged(child);
                child.parent = null;
                return parent;
            }
        }
        return null;
    }

    public void setStatus(Status status, Path...paths) {
        if(status != null && paths != null) {
            for (Path path : paths) {
                if(path != null) {
                    final DataNode child = findOrCreateChild(path);
                    child.setStatus(status);
                }
            }
        }
    }

    public Set<DataNode> getChildren() {
        return children;
    }

    @Override
    public int compareTo(DataNode o) {
        return getRefStr().compareTo(o.getRefStr());
    }

    private String getRefStr() {
        return this.type + (this.reference != null ? this.reference : "");
    }

    public enum Status {
        COMPLETED, NOT_STARTED, IN_PROGRESS, DELETED;
    }

    public static class Path {
        private String[] type;
        private String[] reference;

        public String[] getType() {
            return type;
        }

        public String[] getReference() {
            return reference;
        }

        @Override
        public String toString() {
            if(type != null && reference != null && type.length == reference.length) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < type.length; i++) {
                    sb.append(type[i] + "::" + reference[i]);
                }
                return sb.toString();
            }
            return "Invalid Graph Path";
        }
    }

    /**
     * Path elements are expected to be of the form &lt;&lt;type&gt;&gt;::&lt;&lt;reference&gt;&gt;
     *
     * @param graphPath
     * @return Path formed by provided sequence of Strings
     */
    public static Path path(String... graphPath) {
        final Path path = new Path();
        String[] type = new String[graphPath.length];
        String[] sectionRef = new String[graphPath.length];
        for (int i = 0; i < graphPath.length; i++) {
            final String[] split = graphPath[i].split("::");
            type[i] = split.length < 1 || split[0] == null || split[0].isEmpty() ? null : split[0];
            sectionRef[i] = split.length < 2 || split[1] == null || split[1].isEmpty() ? null : split[1];
        }
        path.type = type;
        path.reference = sectionRef;
        return path;
    }

    @Override
    public String toString() {
        return "DataNode{" +
                "type='" + type + '\'' +
                ", reference='" + reference + '\'' +
                ", status=" + status +
                ", children=" + children +
                '}';
    }


}
