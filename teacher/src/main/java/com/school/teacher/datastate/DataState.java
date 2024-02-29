package com.school.teacher.datastate;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Table(name = "data_state",
        indexes = {@Index(unique = true, columnList = "data_type, data_reference, context")})
public class DataState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_reference")
    private String dataReference;

    @Column(name = "context")
    private String context;

    @Lob
    @Column(name = "root", length = 10000)
    private String root;

    @Version
    @Column(name = "version")
    private Integer version = 1;

    public DataState() {
    }

    public DataState(String dataType, String dataReference, String context) {
        this.dataType = dataType;
        this.dataReference = dataReference;
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataReference() {
        return dataReference;
    }

    public void setDataReference(String dataReference) {
        this.dataReference = dataReference;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DataState{" +
                "id=" + id +
                ", dataType='" + dataType + '\'' +
                ", dataReference='" + dataReference + '\'' +
                ", context='" + context + '\'' +
                ", root='" + root + '\'' +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataState dataState = (DataState) o;
        return id == dataState.id && Objects.equals(dataType, dataState.dataType) &&
                Objects.equals(dataReference, dataState.dataReference) &&
                Objects.equals(context, dataState.context) &&
                Objects.equals(root, dataState.root) &&
                Objects.equals(version, dataState.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataType, dataReference, context, root, version);
    }
}

