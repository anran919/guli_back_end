package com.zar.service_edu.entity.tree_subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Level1 {
    private String id;
    private String title;
    private List<Level2> children =new ArrayList<>();
}
