package xws.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.auth.domain.Report;
import xws.auth.dto.ReportDTO;
import xws.auth.security.TokenUtils;
import xws.auth.service.ReportService;
import xws.auth.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    private TokenUtils tokenUtils;


    @GetMapping("getUsernames/{username2}")
    public ResponseEntity<List<String>> getUsernames(@PathVariable String username2, HttpServletRequest request){
        String username = tokenUtils.getUsernameFromToken(tokenUtils.getToken(request));
        List<String> usernames = new ArrayList<>();
        usernames.add(username);
        usernames.add(username2);

        return new ResponseEntity<>(usernames, HttpStatus.OK);
    }

    @PostMapping("saveReport")
    public ResponseEntity<Report> saveReport(@RequestBody ReportDTO dto){

        return new ResponseEntity<>(reportService.save(dto), HttpStatus.CREATED);
    }
}
