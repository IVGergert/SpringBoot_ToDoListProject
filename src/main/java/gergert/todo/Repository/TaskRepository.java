package gergert.todo.Repository;

import gergert.todo.Entity.Task;
import gergert.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);

    List<Task> findByUserAndCompleted(User user, boolean completed);
    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String keyword);
    List<Task> findByUserAndCompletedAndTitleContainingIgnoreCase(User user, boolean completed, String keyword);

    long countByUser(User user);
    long countByUserAndCompletedTrue(User user);

    Optional<Task> findByIdAndUser(Long id, User user);
}
