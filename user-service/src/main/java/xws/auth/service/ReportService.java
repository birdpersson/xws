package xws.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.auth.domain.Report;
import xws.auth.domain.User;
import xws.auth.dto.ReportDTO;
import xws.auth.repository.ReportRepository;
import xws.auth.repository.UserRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public Report save(ReportDTO report){
        String username = report.getUsername();
        String repUsername = report.getRepUsername();
        String reason = report.getReason();

        System.out.println(username);
        System.out.println(repUsername);
        System.out.println(reason);
        return reportRepository.save(new Report(username, repUsername,reason));
    }

}
