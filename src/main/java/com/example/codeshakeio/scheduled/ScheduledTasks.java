package com.example.codeshakeio.scheduled;


import com.example.codeshakeio.dto.ParentDTO;
import com.example.codeshakeio.dto.StudentDTO;
import com.example.codeshakeio.dto.TeacherDTO;
import com.example.codeshakeio.dto.UserDTO;
import com.example.codeshakeio.externalapirequests.GateKeeperApiRequests;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {

    private final GateKeeperApiRequests gateKeeperApiRequests;

    @Scheduled(cron = "${task-scheduling.cron.run-per-minute}")
    public void checkAndUpdateEdushake() {

        try{
            List<UserDTO> usersFromEdushake = gateKeeperApiRequests.getUsersUsingGET().orElse(new ArrayList<>());

            List<StudentDTO> students = gateKeeperApiRequests.getStudentsUsingGET().orElse(new ArrayList<>());

            List<TeacherDTO> teachers  = gateKeeperApiRequests.getTeachersUsingGET().orElse(new ArrayList<>());

            List<ParentDTO> parents= new ArrayList<>();
            students.forEach(student -> {
                try {
                    ParentDTO  parentDTO = gateKeeperApiRequests.getPersonUsingGET(student.getParentId()).orElse(null);
                    if(null != parentDTO){
                        parents.add(parentDTO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
