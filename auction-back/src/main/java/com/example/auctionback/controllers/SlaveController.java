package com.example.auctionback.controllers;

import com.example.auctionback.controllers.models.ItemRequest;
import com.example.auctionback.controllers.models.ItemResponse;
import com.example.auctionback.controllers.models.SlaveRequest;
import com.example.auctionback.controllers.models.SlaveResponse;
import com.example.auctionback.exceptions.ItemExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.SlaveNotExistException;
import com.example.auctionback.services.ItemService;
import com.example.auctionback.services.SlaveService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class SlaveController {
    private final SlaveService slaveService;
    private final ItemService itemService;

    @GetMapping("/{id}")
    public SlaveResponse getSlave(@PathVariable("id") Long slaveId) throws SlaveNotExistException {
        //todo: get all items
        return slaveService.getSlaveFromDB(slaveId);
    }

    @GetMapping("/get_all")
    public List<SlaveResponse> getSlaves()  {
        return slaveService.getAllSlavesFromDB();
    }

    @PostMapping("/new_client")
    public SlaveResponse slaveRegistration(@RequestBody SlaveRequest slaveRequest) {

        return slaveService.saveSlaveInDB(slaveRequest);
    }

    @PostMapping("/{id}/add_item")
    public ItemResponse addItem(@PathVariable("id") Long slaveId, @RequestBody ItemRequest itemRequest)
            throws ItemExistException, SlaveNotExistException {
        return slaveService.addItem(slaveId, itemRequest);
    }
    @GetMapping("/{id}/get_all_items")
    public List<ItemResponse> getAllItems(@PathVariable("id") Long slaveId)
            throws ItemNotFoundException, SlaveNotExistException {
        return slaveService.getAllUserItemsFromDB(slaveId);
    }


    @DeleteMapping("/{id}")
    public SlaveResponse deleteSlave(@PathVariable("id") Long slaveId)
            throws SlaveNotExistException {
        //todo: delete items this user
        return slaveService.deleteSlave(slaveId);

    }
}
