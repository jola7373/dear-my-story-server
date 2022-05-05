package com.qnnect.user.dtos;

import com.qnnect.cafe.domain.CafeUser;
import com.qnnect.user.domain.Report;
import com.qnnect.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ReportResponse {

    private String nickName;
    private long reportId;

    public static ReportResponse from(User user){
        return new ReportResponse(user.getNickName(),user.getReportId());
    }

    public static List<ReportResponse> listFrom(List<User> reportedUsers) {

        return reportedUsers.stream()
                .map(ReportResponse::from)
                .collect(Collectors.toList());
    }
}
