package com.tempo.testapp.controller;

import com.tempo.testapp.exceptions.GenericException;
import com.tempo.testapp.jwtutil.JwtUtil;
import com.tempo.testapp.entity.HistoryQuery;
import com.tempo.testapp.model.JwtRequest;
import com.tempo.testapp.model.JwtResponse;
import com.tempo.testapp.entity.User;
import com.tempo.testapp.service.HistoryService;
import com.tempo.testapp.service.MyUserDetailsService;
import com.tempo.testapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AppResource {

    /*TODO
    *  Los requerimientos son los siguientes:
1. Debes desarrollar una API REST en Spring Boot con las siguientes funcionalidades:
a. Sign up usuarios.
b. Login usuarios vía JWT.
c. Sumar dos números. Este endpoint debe retornar el resultado de la suma y puede ser consumido solo por usuarios logueados.
d. Historial de todos los llamados al endpoint de suma. Responder en Json, con data paginada y el límite se encuentre en properties.
e. Logout usuarios.
f. El historial y la información de los usuarios se debe almacenar en una database PostgreSQL.
g. Incluir errores http. Mensajes y descripciones para la serie 400.

2. Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker.

3. Debes agregar un Postman Collection o Swagger para que probemos tu API.

4. Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.
* */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(){
        return "INDEX - hello world";
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, Object> userMap){
        System.out.println("TRYING TO LOG IN... PLEASE WAIT.");
        System.out.println(userMap.toString());
        String user_name = (String) userMap.get("user_name");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        System.out.println("PASSWORD: "+password);

        ResponseEntity user = userService.validateUser(user_name, email,password);

        Map<String,String> map = new HashMap<>();
        map.put("msg", "Sesion Iniciada.");

        historyService.addHistoryEndpoint("/login",user_name);

        return user;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String,String>> signup(@RequestBody Map<String, Object> userMap) throws Exception {
        //insert user into db
        String name = (String) userMap.get("name");
        String user_name = (String) userMap.get("user_name");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        String country = (String) userMap.get("country");

        User user = new User();
        user.setName(name);
        user.setUsername(user_name);
        user.setEmail(email);
     //   String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(passwordEncoder.encode(password));
        user.setCountry(country);

        Map<String, String> map = new HashMap<>();

        try{
            User registered = userService.registerUser(user);
            map.put("msg", "Usuario Registrado");
            map.put("user", registered.toString());
        }catch (GenericException e){
            map.put("msg", e.toString().substring(e.toString().lastIndexOf(":")+2));
            System.out.println("Exception ->"+e);
        }
        historyService.addHistoryEndpoint("/signup",user_name);

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/calcularSuma/{a}+{b}")
    public ResponseEntity<Map<String,String>> sumarDosNumeros(@PathVariable("a") int a, @PathVariable("b") int b, HttpServletRequest httpServletRequest){

        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtUtil.getUsernameFromToken(jwt);

        historyService.addHistorySuma("/calcularSuma",username, a,b);

        Map<String, String> map = new HashMap<>();
        map.put("msg", "Resultado: "+(a+b));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/history")
    public List<HistoryQuery> history(@RequestParam("size") Integer size, @RequestParam("page") Integer page, HttpServletRequest httpServletRequest){
        //must be connected to db
        //return json, paginated results. limit from {history.limitperpage}
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtUtil.getUsernameFromToken(jwt);

        historyService.addHistoryEndpoint("/history",username);
        return historyService.getHistory(page, size);
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

}
