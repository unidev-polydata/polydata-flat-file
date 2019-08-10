package com.unidev.polydata.model;

import com.unidev.polydata.domain.BasicPoly;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlatFileModel {

    /**
     * container:metadata
     */
    @Builder.Default
    private HashMap<String, BasicPoly> metadata = new HashMap<>();

    /**
     * {container: data{id: poly}}
     */
    @Builder.Default
    private HashMap<String, HashMap<String, BasicPoly>> data = new HashMap<>();

}
