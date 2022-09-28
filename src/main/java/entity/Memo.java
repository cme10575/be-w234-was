package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User author;
    private String content;
}
