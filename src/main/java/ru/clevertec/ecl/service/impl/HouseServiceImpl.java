package ru.clevertec.ecl.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.service.HouseService;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;
    private final HouseMapper mapper;

    @Override
    public long save(HouseDto house) {
        return houseDao.save(mapper.toHouse(house));
    }

    @Override
    public HouseDto findById(long id) {
        return mapper.toHouseDto(houseDao.findById(id).orElseThrow());
    }

    @Override
    public void update(HouseDto houseDto) {
        houseDao.update(mapper.toHouse(houseDto));
    }

    @Override
    public void delete(UUID uuid) {
        houseDao.delete(uuid);
    }

    @Override
    public List<HouseDto> findAll() {
        return mapper.toListHouseDto(houseDao.findAll());
    }

}
