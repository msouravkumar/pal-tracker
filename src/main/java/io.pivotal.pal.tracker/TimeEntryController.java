package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){
           TimeEntry timeEntryObj = timeEntryRepository.create(timeEntry);
           return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.CREATED);
        //return  null;
    }
    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable Long id){
        TimeEntry timeEntryObj = timeEntryRepository.find(id);
        if(null == timeEntryObj){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        List<TimeEntry> timeEntryObjList = timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntryObjList, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody TimeEntry timeEntry){
        TimeEntry timeEntryObj = timeEntryRepository.update(id, timeEntry);
        if(null == timeEntryObj){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        //if()
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
