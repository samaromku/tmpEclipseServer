package entities;
import database.DBWorker;
import database.Queries;
import entities.UserRole;
import network.Request;
import network.Response;
import network.Server;
import network.UserEnum;
import storage.JsonParser;
import entities.Task;
import entities.User;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientEntity extends Thread {
    private Logger log = Logger.getLogger(ClientEntity.class.getName());
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private String read;
    private String write;
    JsonParser parser = new JsonParser();
    Queries queries = new Queries();
    DBWorker dbWorker = new DBWorker();
    private String clientName;
    private String firstLine;
    private boolean isAuth = false;

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientEntity(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        start();
    }

    @Override
    public void run() {
        try {
            dbWorker.getAllUsersFromDB();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

            while((firstLine = reader.readLine())!=null){
                System.out.println(firstLine);
            checkAuth();
            if (isAuth()) {
                while ((read = reader.readLine()) != null) {
                    if (read.equals("exit")) {
                        break;
                    }
                    log.info("Сообщение от " + getClientName() + read);
                    parseRequest();
                    final String data = write;
                    log.info("Сообщение юзеру " + getClientName() + ": " + write);
                    //вызов метода отправки сообщения всем клиентам
                    // - желательно в отдельном потоке
                    new Thread(new Runnable() {
                        public void run() {
                            server.getClientEntity().writer.println(data);
                            server.getClientEntity().writer.flush();
//                        for (int i = 0; i < server.getClients().size(); i++) {
//                            server.getClients().get(i).writer.println(data);
//                            server.getClients().get(i).writer.flush();
//                        }
                        }
                    }).start();
                }
            }
        }
            writer.close();
            server.remove(this);
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAuth(){
        if(parser.parseFromJson(firstLine).getRequest().equals(Request.AUTH)){
            doAuth();
            String data = write;
            System.out.println("message to " + getClientName() + data);
            new Thread(() -> {
                server.getClientEntity().writer.println(data);
                server.getClientEntity().writer.flush();
            }).start();
        }
    }

     void parseRequest(){
        String request;
        if(parser.parseFromJson(read).getRequest()!=null) {
            request = parser.parseFromJson(read).getRequest();
            switch (request){
                case Queries.CONTROL_TASK:
                    doneTask(Queries.CONTROL_TASK);
                    return;

                case Queries.NEED_HELP:
                    doneTask(Queries.NEED_HELP);
                    return;
                case Queries.DISAGREE_TASK:
                    doneTask(Queries.DISAGREE_TASK);
                    return;

                case Request.ADD_TASK_TO_SERVER:
                    addTask();
                    return;
                case Request.WANT_SOME_COMMENTS:
                    sendCommentsByTask();
                    return;
                case Request.CHANGE_PERMISSION_PLEASE:
                    updateUserRole();
                    return;

//                case Request.GIVE_ME_LAST_USERS_COORDS:
//                    getLatestUsersCoords();
//                    return;

                case Request.REMOVE_TASK:
                    removeTask();
                    return;

                case Request.REMOVE_USER:
                    removeUser();
                    return;

                case Request.GIVE_ME_ADDRESSES_PLEASE:
                    giveAddresses();
                    return;

                case Request.ADD_NEW_ROLE:
                    addNewRole();
                    return;

                case Request.ADD_COORDS:
                    addCoords();
                    return;

                case Request.UPDATE_TASK:
                    updateTask();
                    return;

                case Request.ADD_NEW_USER:
                    addNewUser();
                    return;

                    default:
            }
        }
    }


    private void removeTask(){
        log.info("Удаляем задание");
        if(dbWorker.removeTask(parser.parseFromJson(read).getTask())){
            write = parser.success(Response.SUCCESS_REMOVE_TASK);
        }else write = parser.notSuccess();
    }

    private void removeUser(){
        log.info("Удаляем пользователя");
        if(dbWorker.removeUser(parser.parseFromJson(read).getUser())){
            write = parser.success(Response.SUCCESS_REMOVE_USER);
        }else write = parser.notSuccess();
    }

    private void updateTask(){
        log.info("Обновляем задание");
        if(dbWorker.updateTask(parser.parseFromJson(read).getTask()))
            write = parser.successUpdateTask();
                    else write = parser.notSuccess();
    }

//    private void getLatestUsersCoords(){
//        log.info("Даем последние координаты юзеров");
//        if(dbWorker.getLatestUserCoords())
//            write = parser.parseLastUserCoords(dbWorker.getUserCoordsList());
//        else
//            write = parser.notSuccess();
//    }

    private void addCoords(){
        log.info("Добавляем координаты юзера");
        if(dbWorker.insertUserCoords(parser.parseFromJson(read).getUserCoords()))
        write = parser.successAddCoords();
        else write = parser.notSuccess();
    }

    private void giveAddresses(){
        log.info("Подготавливаем адреса для клиента");
//        dbWorker.getAllAddresses();
//        write = parser.parseAddressesToUser(dbWorker.getAllAddresses());
    }

    private void doneTask(String taskString){
            log.info("Клиент прислал запрос на выполненное задание, с комментарием "+ parser.parseFromJson(read).getComment().getBody());
//            dbWorker.updateTaskStatus(queries.updateTaskStatus(taskString, parser.parseFromJson(read).getComment().getTaskId()));
//            dbWorker.insertComment(queries.insertComment(parser.parseFromJson(read).getComment()));
        write = parser.successAddComment();
    }

    private void addNewUser(){
        User newUser = parser.parseFromJson(read).getUser();
        int userId = (int) (Math.random()*500);
        newUser.setId(userId);
        UserRole userRole = new UserRole(userId, false, false, false, false, false, false, false, false, false, false, false, userId);;
        switch (newUser.getRole()) {
            case UserEnum.ADMIN_ROLE:
                userRole = new UserRole(userId, true, true, true, true, true, true, true, true, true, true, true, userId);
                break;
            case UserEnum.USER_ROLE:
                userRole = new UserRole(userId, false, false, false, false, true, true, false, false, true, true, false, userId);
                break;
            case UserEnum.MANAGER_ROLE:
                userRole = new UserRole(userId, false, true, true, false, true, true, true, true, true, true, false, userId);
                break;
        }
        newUser.setUserRole(userRole);
        if(dbWorker.addNewUserAndUserRole(newUser)) {
//            write = parser.successAddUser(newUser, Response.SUCCESS_ADD_USER);
        }else write = parser.notSuccess();
    }

    private void doAuth(){
        Request authRequest = parser.parseFromJson(firstLine);
        String userName = authRequest.getUser().getLogin();
        String pwd = authRequest.getUser().getPassword();
        //проверка юзера

        User tryToAuth = new User();
        tryToAuth.setLogin(userName);
        tryToAuth.setPassword(pwd);
        List<User> userList = new ArrayList<>();
        userList.addAll(dbWorker.getUserList());
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getLogin().equals(tryToAuth.getLogin()) &&
                userList.get(i).getPassword().equals(tryToAuth.getPassword())){
                tryToAuth = userList.get(i);
            }
        }
        if(tryToAuth.getRole()!=null){
        if(tryToAuth.getRole().equals(UserEnum.ADMIN_ROLE)||tryToAuth.getRole().equals(UserEnum.MANAGER_ROLE)){
            dbWorker.getAllTasksFromDb();
//            write = parser.parseToAdminUsersTask(dbWorker.getUserList(), dbWorker.getTasks(), Response.ADD_ACTION_ADMIN);
//            log.info("юзер " + userName + " это админ ");
            setClientName(userName);
            setAuth(true);
            return;
        } else if(tryToAuth.getRole().equals(UserEnum.USER_ROLE)){
            dbWorker.getTasksFromDbByUserId(String.valueOf(tryToAuth.getId()));
//            write = parser.parseToJsonUserTasks(dbWorker.getUsersForSimpleUser(), tryToAuth,
//                    dbWorker.getTasks(), Response.ADD_TASKS_TO_USER);
//            log.info("юзернейм " + userName + " - это юзер из БД");
            setClientName(userName);
            setAuth(true);
            return;
        }
        } else {
            write = parser.parseToJsonGuest();
            log.info("юзер " + userName + " это гость ");
            setClientName("неавторизованный клиент "+userName);
            setAuth(false);
            return;
        }
//            for(User user : dbWorker.getUserList()) {
//                System.out.println("Юзернейм " + userName + " " + pwd);
//                System.out.println(dbWorker.getUserList().size()+" size");
//                System.out.println( "Юзернейм из массива" +user.getLogin() + " " + user.getPassword());
//                    if (user.getLogin().equals(userName) && user.getPassword().equals(pwd)) {
//                        if (user.getRole().equals(User.ADMIN_ROLE)) {
//                            dbWorker.queryAll();
//                            write = parser.parseToAdminUsersTask(dbWorker.getUserList(), dbWorker.getTasks(), Response.ADD_ACTION_ADMIN);
//                            log.info("юзер " + userName + " это админ ");
//                            setClientName(userName);
//                            setAuth(true);
//                            return;
//                        } else {
//                            dbWorker.queryById(String.valueOf(user.getId()));
//                            write = parser.parseToJsonUserTasks(dbWorker.getUsersForSimpleUser(), user,
//                                    dbWorker.getTasks(), Response.ADD_TASKS_TO_USER);
//                            log.info("юзернейм " + userName + " - это юзер из БД");
//                            setClientName(userName);
//                            setAuth(true);
//                            return;
//                        }
//                    }
////                    else {
////                        write = parser.parseToJsonGuest();
////                        log.info("юзер " + userName + " это гость ");
////                        setClientName("неавторизованный клиент "+userName);
////                        setAuth(false);
////                        return;
////                    }
//            }
        }

        private void addTask(){
                    Task task = parser.parseFromJson(read).getTask();
                    dbWorker.insertTask(task);
                    write = parser.successCreateTask();
        }

        private void updateUserRole(){
            dbWorker.updateUserRole(parser.parseFromJson(read).getUserRole());
            write = parser.parseSuccessUpdateUserRole();
        }

        private void sendCommentsByTask(){
            int id = parser.parseFromJson(read).getTask().getId();
            dbWorker.getCommentsById(id);
//            write = parser.parseCommentsByTask(dbWorker.getComments(), Response.ADD_COMMENTS);
 //           dbWorker.removeOldComments();
        }

        private void addNewRole(){
            if(dbWorker.insertUserRole(parser.parseFromJson(read).getUserRole()))
            write = parser.parseSuccessInsertUserRole();
            else write = parser.notSuccess();
        }
    }
