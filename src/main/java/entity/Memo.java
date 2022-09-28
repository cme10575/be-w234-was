package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue
    private Long id;
    private String author;
    private String content;
}
