package ru.clevertec.house.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.dao.HouseDao;
import ru.clevertec.house.dto.request.HouseRequest;
import ru.clevertec.house.dto.response.HouseResponse;
import ru.clevertec.house.exception.ResourceNotFoundException;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.service.HouseService;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;
    private final HouseMapper mapper;

    @Override
    public HouseResponse findById(UUID uuid) {
        return mapper.toHouseResponse(houseDao.findById(uuid)
            .orElseThrow(() -> new ResourceNotFoundException(uuid)));
    }

    @Override
    public long save(HouseRequest house) {
        return houseDao.save(mapper.toHouse(house));
    }

    @Override
    public void update(HouseRequest houseRequest) {
        houseDao.update(mapper.toHouse(houseRequest));
    }

    @Override
    public void delete(UUID uuid) {
        houseDao.delete(uuid);
    }

    @Override
    public List<HouseResponse> findAll(int page, int pageSize) {
        return mapper.toListHouseResponse(houseDao.findAll(page, pageSize));
    }

    @Override
    public List<HouseResponse> findOwnedHousesByPerson(UUID uuid) {
        return mapper.toListHouseResponse(houseDao.findOwnedHousesByPerson(uuid));
    }

    @Override
    public List<HouseResponse> searchHouses(String text) {
        return mapper.toListHouseResponse(houseDao.searchHouses(text));
    }

}
