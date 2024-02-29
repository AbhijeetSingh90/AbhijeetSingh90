package com.school.teacher.serviceImpl;

import com.school.teacher.datamodel.Section;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "datacapture")
public class DataCaptureConfig {

        private List
                <Section> sections;

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }
}
