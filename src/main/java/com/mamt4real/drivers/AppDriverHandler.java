package com.mamt4real.drivers;
import com.google.common.collect.Lists;
import com.mamt4real.models.Todo;
import com.mamt4real.repositories.*;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class AppDriverHandler {
    private static final UserRepository userRepository = new UserRepository();
    private static final TodoRepository todoRepository = new TodoRepository();
    private static final Logger logger = Logger.getLogger(AppDriverHandler.class);

    private static final HashMap<Integer, Runnable> appHandlers = new HashMap<>() {
        {
            put(1, AppDriverHandler::handleAddTodo);
            put(2, AppDriverHandler::handleViewAllTodos);
            put(3, AppDriverHandler::handleActiveTodo);
            put(4, AppDriverHandler::handleCompletedTodo);
            put(5, AppDriverHandler::handleDeleteTodo);
            put(6, AppDriverHandler::handleUpdateTodo);
            put(7, AppDriverHandler::handleMarkTodoCompleted);
            put(8, AppDriverHandler::handleSearchTodosByName);
            put(9, AppDriverHandler::handleSearchTodosByDescription);
            put(10, AppDriverHandler::handleSearchTodosByDate);
        }
    };

    public static Optional<Runnable> getAppHandler(int n) {
        return Optional.of(appHandlers.get(n));
    }


    private static List<Todo> forLoggedUser(List<Todo> todos){
        if(todos == null) return Lists.newArrayList();
        return todos.stream().filter(todo -> todo.getUserId() == AppDriver.LOGGED_USER).collect(Collectors.toList());
    }

    private static void printTodos(List<Todo> todos, String emptyPrompt){
        if (todos != null && todos.size() > 0){
            System.out.printf("|%-3s|%-20s|%-20s|%-10s|%15s|%n","ID", "Name", "Description", "Status", "Date created");
            todos.stream().map(Todo::toRow).forEach(System.out::println);
        }else{
            logger.info(emptyPrompt);
        }
    }
    public static long handleLogin() {
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        long success = userRepository.login(username, password);
        if (success == 0) logger.error("Invalid Credentials");
        return success;
    }

    public static long handleSignup() {
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        return userRepository.signup(name, username, password);
    }

    public static void handleExit() {
        todoRepository.save();
        userRepository.save();
        logger.info("User logout successfully");
    }

    private static void handleAddTodo() {
        if(AppDriver.LOGGED_USER == null){
            logger.error("You have to be logged In to Create a todo!");
            return;
        }
        System.out.print("Name: ");
        String todoName = AppDriver.in.nextLine();
        System.out.print("Description: ");
        String todoDescription = AppDriver.in.nextLine();
        Todo newTodo = new Todo(todoName, todoDescription, AppDriver.LOGGED_USER);
        long newId = todoRepository.createOne(newTodo);
        logger.info(String.format("Todo (%d) created successfully!", newId));

    }

    private static void handleViewAllTodos() {
      printTodos(
              todoRepository.getUserTodos(AppDriver.LOGGED_USER),
              "You don't have any todos");
    }

    private static void handleActiveTodo() {
       printTodos(
               forLoggedUser(todoRepository.getActiveTodos()),
               "You have No active Todos!"
       );
    }
    private static void handleCompletedTodo() {
        printTodos(
               forLoggedUser(todoRepository.getCompletedTodos()),
               "You haven't completed any of your todos"
        );
    }

    private static void handleDeleteTodo() {
        System.out.println("Todo ID: ");
        long todoId = AppDriver.in.nextLong();
        AppDriver.in.nextLine();
        Optional<Todo> todo = todoRepository.getOne(todoId);
        if (todo.isEmpty()){
            logger.error("No todo with id: " + todoId);
        }else {
            if(todo.get().getUserId() != AppDriver.LOGGED_USER){
                logger.error("You can only delete your todo");
            }else {
                todoRepository.delete(todo.get());
                logger.info("Todo deleted successfully");
            }
        }
    }

    private static void handleUpdateTodo() {
        System.out.print("Todo ID: ");
        long todoId  = AppDriver.in.nextLong();
        AppDriver.in.nextLine();
        Optional<Todo> todoOrNull = todoRepository.getOne(todoId);
        if(todoOrNull.isEmpty()){
            logger.error("Todo not found!");
            return;
        }
         Todo todo = todoOrNull.get();
         System.out.print("Name (enter to skip): ");
         String todoName = AppDriver.in.nextLine();
         System.out.print("Desc (enter to skip): ");
         String desc = AppDriver.in.nextLine();
         if(todoName.length() > 0) todo.setName(todoName);
         if(desc.length() > 0) todo.setDescription(desc);
         Todo updated = todoRepository.update(todo);
         logger.info(String.format("Todo (%s) Updated successfully!", updated.getTodoId()));
    }

    private static void handleMarkTodoCompleted() {
        System.out.print("Todo ID: ");
        long todoId  = AppDriver.in.nextLong();
        AppDriver.in.nextLine();
        todoRepository.markTodoAsCompleted(todoId);
        logger.info("Operation Successful!");
    }

    private static void handleSearchTodosByName() {
        System.out.print("Enter name query: ");
        String query = AppDriver.in.nextLine();
        printTodos(
                forLoggedUser(todoRepository.searchTodosByName(query)),
                "No todos found with name similar to" + query
        );
    }

    private static void handleSearchTodosByDescription() {
        System.out.print("Enter description query: ");
        String query = AppDriver.in.nextLine();
        printTodos(
                forLoggedUser(todoRepository.searchTodosByDescription(query)),
                "No todos found with description similar to" + query
        );
    }

    private static void handleSearchTodosByDate() {
        System.out.print("Enter date query: ");
        String query = AppDriver.in.nextLine();
        printTodos(
                forLoggedUser(todoRepository.searchTodosByDate(query)),
                "No todos found with date similar to" + query
        );
    }

}
