package com.teaminvincible.ESchool.MeetingModule.Controller;

import com.teaminvincible.ESchool.Annotations.DeleteAPI;
import com.teaminvincible.ESchool.Annotations.GetAPI;
import com.teaminvincible.ESchool.Annotations.PostAPI;
import com.teaminvincible.ESchool.Annotations.PutAPI;
import com.teaminvincible.ESchool.MeetingModule.DTO.CreateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.DTO.UpdateMeetingRequest;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.Output.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Schema(name = "Create Meeting", implementation = CreateMeetingRequest.class)
    public ResponseEntity createMeeting(@RequestParam String courseId ,@Valid @RequestBody CreateMeetingRequest createMeetingRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response(
                        HttpStatus.CREATED,
                        true,
                        "Meeting Created Successfully!",
                        meetingService.createMeeting(courseId,createMeetingRequest)
                ));
    }

    @Operation(
            summary = "Update meeting"
    )
    @PutAPI("/update-meeting")
    @Schema(name = "Update Meeting request", implementation = UpdateMeetingRequest.class)
    public ResponseEntity updateMeeting(@Valid @RequestBody UpdateMeetingRequest updateMeetingRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Meeting Updated Successfully!",
                        meetingService.updateMeeting(updateMeetingRequest)
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
            summary = "Get details of a meeting",
            description = "Need to pass a user-id as path variable."
    )
    @GetAPI("/get-details")
    public ResponseEntity getDetailsofAmeeting(@RequestParam String meetingId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(
                        HttpStatus.OK,
                        true,
                        "Fetched meeting details with meeting-id: "+meetingId+" success!",
                        meetingService.getMeetingDetails(meetingId)
                ));
    }

}
