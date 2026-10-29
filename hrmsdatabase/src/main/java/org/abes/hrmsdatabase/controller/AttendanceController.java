package org.abes.hrmsdatabase.controller;

import org.abes.hrmsdatabase.model.Attendance;
import org.abes.hrmsdatabase.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("v1/user/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/by-employee/{empId}")
    public List<Attendance> getAttendanceForEmployee(@PathVariable long empId) {
        return attendanceService.getAttendanceForEmployee(empId);
    }

    @PostMapping("/markattendance")
    public String markAttendance(@RequestBody Attendance attendance){
        System.out.println(attendance.getInTime());
        return attendanceService.markAttendance(attendance);
    }

}
