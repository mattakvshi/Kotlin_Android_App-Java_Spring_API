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

@Slf4j
@RestController
@RequestMapping( "/shcool")
public class OnlineStoreController {

    private final OlympiadsService departmentService;
    private final PupilsService orderService;

    @Autowired
    public OnlineStoreController(OlympiadsService departmentService, PupilsService pupilsService) {
        this.departmentService = departmentService;
        this.orderService = pupilsService;
    }


    //OLYMPIADS

    @GetMapping( "/olympiads")
    public ResponseEntity<?> getAllDepartments() {
        return new ResponseEntity<> (departmentService.getAllOlympiads(), HttpStatus.OK);
    }

    @PostMapping("/olympiads")
    public ResponseEntity<?> handleDepartmentPost(@RequestBody OlympiadsActionRequest request) {
        int action = request.getAction();
        Olympiads olympiads = request.getOlympiads();

        switch (action) {
            case 11:
                return new ResponseEntity<>(departmentService.addOlympiads(olympiads), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(departmentService.updateOlympiads(olympiads), HttpStatus.OK);
            case 13:
                departmentService.deleteOlympiads(olympiads);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //PUPILS

    @GetMapping("/pupils")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<> (orderService.getAllPupils(), HttpStatus.OK);
    }

    @PostMapping("/pupils")
    public ResponseEntity<?> handleOrderPost(@RequestBody PupilsActionRequest request) {
        int action = request.getAction();
        Pupils pupils = request.getPupils();

        switch (action) {
            case 11:
                return new ResponseEntity<>(orderService.addPupils(pupils), HttpStatus.CREATED);
            case 12:
                return new ResponseEntity<>(orderService.updatePupils(pupils), HttpStatus.OK);
            case 13:
                orderService.deletePupils(pupils);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
