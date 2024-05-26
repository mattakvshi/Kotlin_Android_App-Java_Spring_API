package ru.mattakvshi.ShcoolOlympiads.Controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.mattakvshi.ShcoolOlympiads.RequestDividers.OlympiadsActionRequest;
import ru.mattakvshi.ShcoolOlympiads.RequestDividers.PupilsActionRequest;
import ru.mattakvshi.ShcoolOlympiads.Models.Olympiads;
import ru.mattakvshi.ShcoolOlympiads.Models.Pupils;
import ru.mattakvshi.ShcoolOlympiads.Service.OlympiadsService;
import ru.mattakvshi.ShcoolOlympiads.Service.PupilsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping( "/shcool")
public class SchoolOlympiadsController {

    private final OlympiadsService olympiadsService;
    private final PupilsService pupilsService;

    @Autowired
    public SchoolOlympiadsController(OlympiadsService departmentService, PupilsService pupilsService) {
        this.olympiadsService = departmentService;
        this.pupilsService = pupilsService;
    }


    //OLYMPIADS

//    @GetMapping( "/olympiads")
//    public ResponseEntity<?> getAllDepartments() {
//        return new ResponseEntity<> (departmentService.getAllOlympiads(), HttpStatus.OK);
//    }

    @GetMapping( "/olympiads")
    public ResponseEntity<?> getAllOlympiads() {
        List<Olympiads> olympiads = olympiadsService.getAllOlympiads();
        Map<String, List<Olympiads>> response = new HashMap<>();
        response.put("result", olympiads);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/olympiads")
    public ResponseEntity<?> handleOlympiadsPost(@RequestBody OlympiadsActionRequest request) {
        int action = request.getAction();
        Olympiads olympiads = request.getOlympiads();

        switch (action) {
            case 11:
                return new ResponseEntity<>(olympiadsService.addOlympiads(olympiads), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(olympiadsService.updateOlympiads(olympiads), HttpStatus.OK);
            case 13:
                olympiadsService.deleteOlympiads(olympiads);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //PUPILS

//    @GetMapping("/pupils")
//    public ResponseEntity<?> getAllOrders() {
//        return new ResponseEntity<> (orderService.getAllPupils(), HttpStatus.OK);
//    }

    @GetMapping( "/pupils")
    public ResponseEntity<?> getAllPupils() {
        List<Pupils> olympiads = pupilsService.getAllPupils();
        Map<String, List<Pupils>> response = new HashMap<>();
        response.put("result", olympiads);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/pupils")
    public ResponseEntity<?> handlePupilsPost(@RequestBody PupilsActionRequest request) {
        int action = request.getAction();
        Pupils pupils = request.getPupils();

        switch (action) {
            case 11:
                return new ResponseEntity<>(pupilsService.addPupils(pupils), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(pupilsService.updatePupils(pupils), HttpStatus.OK);
            case 13:
                pupilsService.deletePupils(pupils);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
