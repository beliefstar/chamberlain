package com.zhenxin.chamberlain.controller;

import com.zhenxin.chamberlain.dao.pojo.Consume;
import com.zhenxin.chamberlain.dto.ConsumeQueryDTO;
import com.zhenxin.chamberlain.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xzhen
 * @created 14:37 26/02/2019
 * @description TODO
 */
@RestController
public class ConsumeController {

    @Autowired
    private ConsumeService consumeService;

    @PostMapping("/save")
    public Consume save(@RequestBody Consume consume) {
        return consumeService.save(consume);
    }

    @GetMapping("/delete")
    public String delete(String consumeId) {
        consumeService.delete(consumeId);
        return "ok";
    }

    @PostMapping("/update")
    public Consume update(@RequestBody Consume consume) {
        return consumeService.update(consume);
    }

    @PostMapping("/select")
    public List<Consume> select(@RequestBody ConsumeQueryDTO consume) {
        return consumeService.select(consume);
    }
}
