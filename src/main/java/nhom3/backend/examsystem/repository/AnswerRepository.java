package nhom3.backend.examsystem.repository;

import nhom3.backend.examsystem.model.Answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

}
