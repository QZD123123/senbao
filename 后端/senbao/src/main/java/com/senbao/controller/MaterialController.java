package com.senbao.controller;

import com.senbao.pojo.Material;
import com.senbao.service.MaterialService;
import com.senbao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("")
    public Result materialPageSelect(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String type) {
        Result result = materialService.materialPageSelect(page, pageSize, type);
        return result;
    }

    @GetMapping("getall")
    public Result getMaterial() {
        Result result = materialService.getMaterial();
        return result;
    }

    @PostMapping
    public Result addMaterial(@RequestBody Material material) {
        Result result = materialService.addMaterial(material);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteMaterial(@PathVariable Integer id) {
        Result result = materialService.deleteMaterial(id);
        return result;
    }

    @PatchMapping("/{id}")
    public Result updateMaterial(
            @PathVariable Integer id,
            @RequestBody Material material) {
        Result result = materialService.updateMaterial(id, material);
        return result;
    }
}
