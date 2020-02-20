package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){
           TimeEntry timeEntryObj = timeEntryRepository.create(timeEntry);
            actionCounter.increment();
            timeEntrySummary.record(timeEntryRepository.list().size());
           return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.CREATED);
        //return  null;
    }
    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable Long id){
        TimeEntry timeEntryObj = timeEntryRepository.find(id);
        if(null == timeEntryObj){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            actionCounter.increment();
        }
        return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        List<TimeEntry> timeEntryObjList = timeEntryRepository.list();
        actionCounter.increment();
        return new ResponseEntity<List<TimeEntry>>(timeEntryObjList, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody TimeEntry timeEntry){
        TimeEntry timeEntryObj = timeEntryRepository.update(id, timeEntry);
        if(null == timeEntryObj){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            actionCounter.increment();
        }
        return new ResponseEntity<TimeEntry>(timeEntryObj, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        //if()
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
