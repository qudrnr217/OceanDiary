package com.oceandiary.api.room.controller;

import com.oceandiary.api.common.utils.CookieUtils;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.room.response.RoomResponse;
import com.oceandiary.api.room.service.RoomService;
import com.oceandiary.api.user.security.userdetails.CurrentUser;
import com.oceandiary.api.user.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("")
    public RoomResponse.CreateRoom createRoom(@RequestPart(value = "form") RoomRequest.CreateRoom request, @RequestPart(value = "file") MultipartFile file, @CurrentUser CustomUserDetails user) {
        return roomService.createRoom(request, file, user.getUser());
    }

    @PostMapping("/{roomId}")
    public RoomResponse.EnterRoom enterRoom(@PathVariable("roomId") Long roomId,
                                            @RequestBody RoomRequest.EnterRoom request,
                                            @CurrentUser CustomUserDetails user,
                                            @CookieValue(name = "name", defaultValue = "") String name) throws UnsupportedEncodingException {
        return roomService.enterRoom(request, roomId, Optional.ofNullable(user).map(CustomUserDetails::getUser).orElse(null), CookieUtils.getCookie(name));
    }

    @DeleteMapping("/{roomId}/participants/{participantId}")
    public void exitRoom(@PathVariable("roomId") Long roomId,
                         @PathVariable Long participantId,
                         @CurrentUser CustomUserDetails user) {
        roomService.exitRoom(roomId, participantId, Optional.ofNullable(user).map(CustomUserDetails::getUser).orElse(null));
    }

    @GetMapping("")
    public Page<RoomResponse.SearchRooms> search(RoomRequest.RoomSearchCondition condition, @PageableDefault(size = 5) Pageable pageable) {
        return roomService.search(condition, pageable);
    }

    @GetMapping("/{roomId}/info")
    public RoomResponse.RoomInfo roomInfo(@PathVariable(name = "roomId") Long roomId) {
        return roomService.getRoomInfo(roomId);
    }

    @GetMapping("/{roomId}/detail")
    public RoomResponse.RoomDetail roomDetail(@PathVariable(name = "roomId") Long roomId) {
        return roomService.getRoomDetail(roomId);
    }

    @PatchMapping("/{roomId}/info")
    public void updateRoomInfo(@PathVariable(name = "roomId") Long roomId, @RequestPart(value = "form") RoomRequest.UpdateRoom request, @RequestPart(value = "file") MultipartFile file, @CurrentUser CustomUserDetails user) {
        roomService.updateRoomInfo(roomId, request, file, user.getUser());
    }

    @PostMapping("/{roomId}/participants/{participantId}")
    public void dropoutParticipant(@PathVariable(name = "roomId") Long roomId, @PathVariable("participantId") Long participantId, @CurrentUser CustomUserDetails user) {
        roomService.dropoutParticipant(roomId, participantId, user.getUser());
    }

}
