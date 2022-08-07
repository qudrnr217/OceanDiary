package com.oceandiary.api.room.controller;

import com.oceandiary.api.room.dto.RoomSearchCondition;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.room.response.RoomResponse;
import com.oceandiary.api.room.service.RoomService;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.security.userdetails.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/")
    public RoomResponse.CreateRoom createRoom(@RequestPart(value = "form") RoomRequest.CreateRoom request, @RequestPart(value = "file") MultipartFile file, @CurrentUser User user) {
        // TODO: image 업로드 구현
        return roomService.createRoom(request, user);
    }

    @PostMapping("/{roomId}")
    public RoomResponse.EnterRoom enterRoom(@PathVariable("roomId") Long roomId, @RequestBody RoomRequest.EnterRoom request, @CurrentUser User user) {
        return roomService.enterRoom(request, roomId, user);
    }

    @DeleteMapping("/{roomId}/participants/{participantId}")
    public void exitRoom(@PathVariable("roomId") Long roomId, @PathVariable Long participantId, @CurrentUser User user) {
        roomService.exitRoom(roomId, participantId, user);
    }

    @GetMapping("/room")
    public Page<RoomResponse.SearchRooms> search(RoomSearchCondition condition, Pageable pageable) {
        return roomService.search(condition, pageable);
    }

}
