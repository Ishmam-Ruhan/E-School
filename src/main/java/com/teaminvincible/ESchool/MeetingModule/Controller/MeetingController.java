package com.teaminvincible.ESchool.MeetingModule.Controller;

import com.teaminvincible.ESchool.Annotations.DeleteAPI;
import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Annotations.PutAPI;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.Output.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/meetings-management")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Operation(
            summary = "Create a meeting"
    )
    @PostAPI("/create-meeting")
    public ResponseEntity createMeeting(@RequestBody Meeting meeting){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response(
                        HttpStatus.CREATED,
                        true,
                        "Meeting Created Successfully!",
                        meetingService.createMeeting(meeting)
                ));
    }

    @Operation(
            summary = "Update a meeting"
    )
    @PutAPI("/update-meeting")
    public ResponseEntity updateMeeting(@RequestBody Meeting meeting){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Meeting Updated Successfully!",
                        meetingService.updateMeeting(meeting)
                ));
    }

    @Operation(
            summary = "Delete a meeting"
    )
    @DeleteAPI("/delete-meeting")
    public ResponseEntity deleteMeeting(@RequestParam String meetingId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Meeting deleted Successfully!",
                        meetingService.deleteMeeting(meetingId)
                ));
    }

    @Operation(
            summary = "Delete all meetings"
    )
    @DeleteAPI("/delete-meeting/all")
    public ResponseEntity deleteMultipleMeeting(List<String> meetingIds){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "All Meetings deleted Successfully!",
                        meetingService.deleteCollectionOfMeeting(meetingIds)
                ));
    }

    @Operation(
            summary = "Get all meetings of a user",
            description = "Need to pass a user-id as path variable."
    )
    @GetAPI("/get-meeting/user/{id}")
    public ResponseEntity getMeetingsOfaUser(@PathVariable("id") String id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Retrieve all meetings with user-id: "+id+" success!",
                        meetingService.getAllMeetingsFromUser(id)
                ));
    }

    @Operation(
            summary = "Get details of a meeting",
            description = "Need to pass a user-id as path variable."
    )
    @GetAPI("/get-meeting/details/meeting/{meetingId}")
    public ResponseEntity getDetailsofAmeeting(@PathVariable("meetingId") String meetingId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Fetched meeting details with meeting-id: "+meetingId+" success!",
                        meetingService.getMeetingDetails(meetingId)
                ));
    }

    @Operation(
            summary = "Get all meetings of a user",
            description = "Need to pass a user-id as path variable."
    )
    @GetAPI("/get-meeting/user/{userId}")
    public ResponseEntity getAllMeetingsFromAUser(@PathVariable("userId") String userId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Fetched all meetings with user-id: "+userId+" success!",
                        meetingService.getAllMeetingsFromUser(userId)
                ));
    }

    @Operation(
            summary = "Get all meetings of a course",
            description = "Need to pass a course-id as path variable."
    )
    @GetAPI("/get-meeting/course/{courseId}")
    public ResponseEntity getAllMeetingsFromACourse(@PathVariable("courseId") String courseId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Fetched all meetings with course-id: "+courseId+" success!",
                        meetingService.getAllMeetingsFromCourse(courseId)
                ));
    }
}
