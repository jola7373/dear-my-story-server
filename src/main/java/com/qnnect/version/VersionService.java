package com.qnnect.version;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionInfoRepository versionRepository;

    public Boolean checkIsVersionValid(EOs os, String currentVersion){
        VersionInfo latestVersion = versionRepository.findByOsAndVersion(os, currentVersion);
        return (latestVersion != null) ? true : false;
    }
}
