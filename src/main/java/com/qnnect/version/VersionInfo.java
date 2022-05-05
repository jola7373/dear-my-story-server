package com.qnnect.version;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class VersionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EOs os;

    private String version;
}
