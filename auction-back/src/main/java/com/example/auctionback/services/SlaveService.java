package com.example.auctionback.services;

import com.example.auctionback.controllers.models.ItemRequest;
import com.example.auctionback.controllers.models.ItemResponse;
import com.example.auctionback.controllers.models.SlaveRequest;
import com.example.auctionback.controllers.models.SlaveResponse;
import com.example.auctionback.entities.Item;
import com.example.auctionback.entities.Slave;
import com.example.auctionback.exceptions.ItemExistException;
import com.example.auctionback.exceptions.ItemNotFoundException;
import com.example.auctionback.exceptions.SlaveNotExistException;
import com.example.auctionback.repository.ItemRepository;
import com.example.auctionback.repository.SlaveRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SlaveService {

    private final SlaveRepository slaveRepository;
    private final ItemRepository itemRepository;

    public SlaveResponse saveSlaveInDB(SlaveRequest slaveRequest) {

        Slave slave = new Slave(slaveRequest.getName(),
                slaveRequest.getMoney(),
                slaveRequest.getReversedMoney(),
                slaveRequest.getPassword());

        slaveRepository.save(slave); // add in db

        return new SlaveResponse(
                slave.getId(),
                slave.getName(),
                slave.getMoney(),
                slave.getReservedMoney(),
                slave.getPassword());
    }

    public SlaveResponse getSlaveFromDB(Long slaveId) throws SlaveNotExistException {

        Optional<Slave> existedSlave = slaveRepository.findById(slaveId);
        Slave slave = existedSlave.orElseThrow(SlaveNotExistException::new); // по идее нахер не нужно ибо такая ошибка тут невозможна

        return new SlaveResponse(slaveId,
                slave.getName(),
                slave.getMoney(),
                slave.getReservedMoney(),
                slave.getPassword());
    }
    public List<SlaveResponse> getAllSlavesFromDB() {
        List<Slave> allSlaves = (List<Slave>) slaveRepository.findAll();

        return  allSlaves.stream()
                .map(slave -> new SlaveResponse(
                        slave.getId(),
                        slave.getName(),
                        slave.getMoney(),
                        slave.getReservedMoney(),
                        slave.getPassword()
                )).collect(Collectors.toList());
    }

    public List<ItemResponse> getAllUserItemsFromDB(Long slaveId)
                                                    throws ItemNotFoundException, SlaveNotExistException {
        slaveRepository.findById(slaveId).orElseThrow(SlaveNotExistException::new);

        Optional<List<Item>> items = itemRepository.findByOwnerId(slaveId);
        List<Item> allSlaveItems = items.orElseThrow(ItemNotFoundException::new);
        return  allSlaveItems.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getOwnerId()
                )).collect(Collectors.toList());
    }

    public ItemResponse addItem(Long slaveId, ItemRequest itemRequest)
                                                    throws ItemExistException, SlaveNotExistException {

        slaveRepository.findById(slaveId).orElseThrow(SlaveNotExistException::new);
        Item item = new Item(itemRequest.getName(),
                itemRequest.getDescription(),
                slaveId);

        itemRepository.save(item); // add in db

        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwnerId());
    }
}
