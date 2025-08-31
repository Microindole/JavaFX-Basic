package org.csu.demo.yuanqi.controller;// in package ...controller
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.csu.demo.yuanqi.mapper.PlayerMapper;
import org.csu.demo.yuanqi.entity.Player;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PlayerMapper playerMapper;

    // A DTO (Data Transfer Object) for login requests
    record LoginRequest(String username, String password) {}

    @PostMapping("/register")
    public ResponseEntity<String> registerPlayer(@RequestBody LoginRequest request) {
        // Use a QueryWrapper to check if the username exists
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.username());
        if (playerMapper.exists(queryWrapper)) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        Player newPlayer = new Player();
        newPlayer.setUsername(request.username());
        newPlayer.setPassword(request.password()); // Remember to hash this in a real app
        playerMapper.insert(newPlayer); // Use the insert method from BaseMapper
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginPlayer(@RequestBody LoginRequest request) {
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.username());
        Player player = playerMapper.selectOne(queryWrapper);

        if (player != null && player.getPassword().equals(request.password())) {
            // In a real app, you would return a JWT (JSON Web Token) here
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}