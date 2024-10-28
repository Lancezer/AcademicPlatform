# Academic Platform

## —— 一次面向对象思想与设计模式的探索

这是我的第一个`Java`项目。虽说项目是为了完成作业而开发，但是这是我第一次使用面向对象思想编写代码，也是应用设计模式开发的第一次尝试。

### 项目简介

该项目实现了一个简易的教务管理系统，包括学生，教师和管理员的基本信息管理，课程信息管理，选课退课等功能。

- 以下是本系统的所有命令与功能：

|               命令               | 功能      |
|:------------------------------:|:--------|
|             `quit`             | 关机      |
| `register 学工号 姓名 密码 确认密码 身份类型` | 用户注册    |
|         `login 学工号 密码`         | 用户登录    |
|         `logout [学工号]`         | 用户登出    |
|          `switch 学工号`          |  切换用户   |
|       `printInfo [学工号]`        | 打印用户信息  |
| `createCourse 课程名称 课程时间 学分 学时` | 创建课程    |
|       `listCourse [工号]`        | 查看课程    |
|       `selectCourse 课程号`       | 选择课程    |
|       `cancelCourse 课程号`       | 注销课程    |
|     `inputCourseBatch 路径`      | 批量导入课程  |
|     `outputCourseBatch 路径`     | 批量导出课程  |
|       `listStudent 课程号`        | 查看选课学生  |
|   `removeStudent 学工号 [课程号]`    |  移除选课学生 |
|  `listCourseSchedule [学工号]`    | 查看课表    |

### 项目设计

#### 命令模式

看到如此多的命令，首先想到命令模式：将具体操作与命令执行分开，将操作最小模块化，再通过不同的命令对象组合操作，实现命令功能。这样做可以很方便地进行扩展，同时保证了数据的安全性，不易被人为修改。于是便有了`Command`接口，并定义了三个抽象方法：`argsNumCheck`，`checkCommand`以及`execute`，用于命令参数的检查与命令的执行。由于这个接口中还实现了部分命令的共有方法，所以我将其定义为抽象类，并在其中编写了许多通用方法和一些字符串常量。需要注意的是，由于这是一个抽象类，所以内部的所有方法都要声明为静态的，才能被调用。如此一来，代码的封装性和可复用性大大提高。

对于每一个具体命令类，我让他们全部继承了`Command`抽象类，并使用简单工厂模式来创建命令对象。没有用更复杂的工厂模式，是因为这个项目中的命令种类并不多，且各个命令相互独立，不需要考虑更复杂的创建流程。~~（PS：本来使用命令模式还考虑了为迭代预留撤销操作，结果作业没有涉及）~~

#### 数据管理

在这个项目中，数据可以分为用户和课程两类，他们之间有着许多关系。所以我借鉴了数据库的管理方法，定义了`Database`来存储用户表与课程表，方便快速查找。而用户与课程之间的关系则存在对应的`User`和`Course`类中，便于访问修改。同时还定义了`State`类来保存系统中的在线用户状态，方便在不同的命令中进行判断。同时，我还定义了`CourseTime`工具类来专门处理课程时间相关功能，实现更好的封装。

#### 异常处理

在这个项目中，命令的检查是一大难点，而我的解决方案是将报错信息放进抛出的异常中，再将异常逐层抛出，在主函数中进行处理，同时将命令的检查模块化。做到一个模块专门检查一个类型的错误。这样一来，代码的可读性，可维护性和可复用性都大大提高，也无需考虑多种异常出现的输出优先级问题。

如此一来，整个项目的架构就清晰起来，接下来开始具体实现。

### 项目实现

#### 通用方法

##### 正则表达式匹配字符串格式

从以上命令不难看出，输入的参数种类繁多，每一种都需要进行格式检查。所以我定义了一个`strCheck`方法，用于检查输入的参数是否符合正则表达式。这样一来，只需要在命令中调用这个方法，就可以很方便地进行参数检查。值得注意的是，因为有时字符串可以有多种格式，所以我在这个方法中使用了可变参数，可以传入多个正则表达式，只要有一个匹配即可。

> **public static void strCheck(String str, String errMessage, String... format) throws IllegalArgumentException**
>
> - **功能**：检查字符串是否符合正则表达式，如不符合则抛出对应异常
> - **传入参数**：
>   - `String str`：待检查的字符串
>   - `String errMessage`：错误信息
>   - `String... format`：匹配格式
> - **返回值**：无
> - **抛出异常**：`IllegalArgumentException`：参数格式错误

##### 各种共用的检查方法

在这个项目中，有许多共用的检查方法，如检查用户是否登录，检查用户是否存在，检查用户是否有权限等。这些方法都是在`Command`抽象类中定义的，方便在各个命令中调用。这些方法的设计思想与上面的字符串匹配类似，都是实现的通用方法，只需要传入对应的参数即可，详情可以查看代码。

#### 异常处理实现

上文提到，在命令类中预留了`checkCommand`方法，在每一个具体的命令类中实现检查。于是我们可以模块化每一个检查功能，将其封装在一个方法中，再在`checkCommand`中调用。这样一来，在调用`checkCommand`时，如果出现异常，那么后面的检查都将不会执行，而直接在主函数中接收抛出的异常，打印错误信息，然后执行下一条命令。

- 以下是一个示例：

```java
    // CreateCourse.java
    // other methods
    public void checkCommand() {
        argsNumCheck(args.length);
        curOnlineUserCheck();
        permissionCheck(State.getCurOnlineUser(), ERR_MSG[13], User.Identity.TEACHER);
        courseNumCheck();
        strCheck(args[0], ERR_MSG[16], ARG_FORMAT[7]);
        courseNameExistCheck(0);
        setCourseTime(args[1]);
        courseConflictCheck(courseTime);
        creditCheck();
        periodCheck();
    }
```

```java
    // Test.java
    // IO implement
    try {
        Command command = Command.existCheck(str);
        command.checkCommand();
        command.execute();
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
```

#### 其他操作实现

在这个项目中，还有许多其他操作，如数据的增删查输出与比较逻辑等。这些操作的实现较为简易，这里不再赘述，详情可以查看代码。

如此一来，这个项目就完成了。

### 致谢

最后，非常感谢你能看到这里。这是我第一次写技术分享文，希望能让你理解项目的逻辑。如果有任何问题，欢迎留言讨论。谢谢！

## - The End -
