package services;

import model.Task;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServices userServices;

    public Task findTaskById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada. Id: "+ id + "Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task createTask(Task task){

        User user = this.userServices.findUserById(task.getUser().getId());
        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);
        return task;

    }

    @Transactional
    public Task updateTask(Task task){

        Task newTask = findTaskById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void deteleTask(Long id){
        findTaskById(id);

        try{
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir a task pois há entidades relacionadas.");
        }
    }

}
