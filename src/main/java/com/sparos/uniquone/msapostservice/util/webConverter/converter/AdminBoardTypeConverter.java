package com.sparos.uniquone.msapostservice.util.webConverter.converter;

import com.sparos.uniquone.msapostservice.admin.domain.BoardType;
import org.springframework.core.convert.converter.Converter;

public class AdminBoardTypeConverter implements Converter<String, BoardType> {
    @Override
    public BoardType convert(String boardType) {
        return BoardType.valueOf(boardType);
    }
}
