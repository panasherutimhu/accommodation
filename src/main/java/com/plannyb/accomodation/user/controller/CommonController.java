//package com.plannyb.accomodation.user.controller;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.plannyb.accomodation.host.service.HostService;
//import com.plannyb.accomodation.user.model.User;
//import com.plannyb.accomodation.user.model.dto.UserDto;
//import com.plannyb.accomodation.user.model.dto.UserPostDto;
//import com.plannyb.accomodation.user.service.ImageService;
//import com.plannyb.accomodation.user.service.UserService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.security.Principal;
//
//import static com.plannyb.accomodation.utils.Helpers.convertToJson;
//
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/secure")
//@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
//public class CommonController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private HostService hostService;
//
//    @Autowired
//    private com.project.homerent.service.ReservationService reservationService;
//
//    @Autowired
//    private MessageService messageService;
//
//    @Autowired
//    private ImageService imageService;
//
//    @GetMapping("/user")
//    public ResponseEntity<String> getUser(Principal principal) throws JsonProcessingException {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(userService.findDtoById(user.getId())));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"User not found\"}");
//    }
//
//    @DeleteMapping("/user/delete")
//    public ResponseEntity<String> simpleUpdate(Principal principal) throws JsonProcessingException {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent()) {
//            userService.deleteById(user.getId());
//            return ResponseEntity.ok().body("{\"Status\": \"Successful Deletion\"}");
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"User not found\"}");
//        }
//    }
//
//    @PutMapping("/user/update")
//    public ResponseEntity<String> updateUserAndPassword(@RequestBody @Nullable UserPostDto userPostDto, Principal principal) throws JsonProcessingException {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(userService.save(userPostDto)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"User not found\"}");
//    }
//
//    @GetMapping("/user/{id}/image")
//    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws Exception {
//        UserDto userDto = userService.findDtoById(id);
//
//        if(userDto!=null) {
//            if(userDto.getImage() != null) {
//                byte[] byteArray = new byte[userDto.getImage().length];
//                int i = 0;
//
//                for (Byte wrappedByte : userDto.getImage()) {
//                    byteArray[i++] = wrappedByte; //auto unboxing
//                }
//                response.setContentType("image/jpeg");
//                InputStream is = new ByteArrayInputStream(byteArray);
//                IOUtils.copy(is, response.getOutputStream());
//            }
//        }
//    }
//
//    @PostMapping("/home/book")
//    public ResponseEntity<String> booking(@RequestBody ReservationDto reservationDto, Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(reservationService.save(reservationDto)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error booking the room\"}");
//    }
//
//    @PutMapping("/home/book")
//    public ResponseEntity<String> updateBooking(@RequestBody ReservationDto reservationDto, Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(reservationService.save(reservationDto)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error updating booking\"}");
//    }
//
//    @PostMapping("/message/send")
//    public ResponseEntity<String> messageSend(@RequestBody MessageDto messageDto, Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(messageService.save(messageDto)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error sending the message\"}");
//    }
//
//    @DeleteMapping("/message/{id}")
//    public ResponseEntity<String> DeleteMessage(@PathVariable("id") Long id) {
//        messageService.deleteById(id);
//        return ResponseEntity.ok().body("{\"Status\": \"Successful Message Deletion\"}");
//    }
//
//    @GetMapping("/messages/receivedfrom/{id}")
//    public ResponseEntity<String> messagesReceivedFromUser(@PathVariable("id") Long id,Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(messageService.findMessagesBySenderId(id)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error sending the message\"}");
//    }
//
//    @GetMapping("/messages/sender/{sender}/receiver/{receiver}")
//    public ResponseEntity<String> history(@PathVariable("sender") Long sender,@PathVariable("receiver") Long receiver,Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(messageService.findHistory(sender, receiver)));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error with user id\"}");
//    }
//
//    @GetMapping("/messages")
//    public ResponseEntity<String> historyList(Principal principal) throws Exception {
//        User user = userService.findByUsername(principal.getName());
//        if(user.getRoles().stream().findFirst().isPresent())
//            return ResponseEntity.ok().body(convertToJson(messageService.findMessageConnections(user.getId())));
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Status\": \"Error with user id\"}");
//    }
//}