package org.drk.pelicula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una película con sus atributos básicos.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {

    private Integer id;
    private String titulo;
    private String genero;
    private Integer año;
}
