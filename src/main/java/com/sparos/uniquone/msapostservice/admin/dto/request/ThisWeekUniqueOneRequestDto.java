package com.sparos.uniquone.msapostservice.admin.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ThisWeekUniqueOneRequestDto {
    private List<Long> cornId = new ArrayList<>();
}
