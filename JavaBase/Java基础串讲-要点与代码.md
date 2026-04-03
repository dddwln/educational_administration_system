# Java 基础串讲 — 要点与代码示例（详解版）

> 适用于课堂串讲、复习与自学。每节含：**为什么要学**、**要点**、**易错点**、**带注释的完整代码**。可在 IDEA 中逐类创建文件后运行 `main`。

---

## 目录

1. [程序入口与注释](#1-程序入口与注释)
2. [数据类型与变量](#2-数据类型与变量)
3. [运算符与流程控制](#3-运算符与流程控制)
4. [数组](#4-数组)
5. [方法](#5-方法)
6. [面向对象：封装](#6-面向对象封装)
7. [构造方法与 this / static](#7-构造方法与-this--static)
8. [继承与多态](#8-继承与多态)
9. [抽象类与接口](#9-抽象类与接口)
10. [枚举与包](#10-枚举与包)
11. [常用类：String 与 Object](#11-常用类string-与-object)
12. [包装类与自动装箱](#12-包装类与自动装箱)
13. [异常](#13-异常)
14. [泛型入门](#14-泛型入门)
15. [集合框架](#15-集合框架)
16. [Lambda 与 Stream 入门](#16-lambda-与-stream-入门)
17. [串讲脉络小结](#17-串讲脉络小结)

---

## 1. 程序入口与注释

### 为什么要讲

- JVM 从固定签名的 `main` 开始执行，写错一个字都无法运行。
- 注释是团队协作与后期维护的基础；Javadoc 可生成 API 文档。

### 要点

| 概念 | 说明 |
|------|------|
| `public class` | 若类是 `public`，**文件名必须与类名完全一致**（含大小写）。 |
| `main` | 必须是 `public static void main(String[] args)`；`args` 接收命令行参数。 |
| 注释类型 | `//` 单行；`/* */` 多行；`/** */` 文档注释，可配合 Javadoc 工具。 |

### 易错点

- 把 `main` 写成 `Main` 或漏写 `static` → 运行报错「找不到 main 方法」。
- 一个 `.java` 文件里只能有一个 **`public` 顶层类**（内部类另说）。

```java
/**
 * 类级文档注释：说明这个类是干什么的（可写 @author、@since 等 Javadoc 标签）。
 * 演示：程序入口与三种注释的写法。
 */
public class Demo01Hello {

    /**
     * 程序入口。JVM 启动时会在该类中查找此方法并调用。
     *
     * @param args 命令行参数。例如在终端执行：java Demo01Hello a b
     *             则 args[0] 为 "a"，args[1] 为 "b"
     */
    public static void main(String[] args) {
        // 单行注释：说明下面一行或紧邻代码在做什么
        System.out.println("Hello, Java");

        /*
         * 多行注释：适合暂时屏蔽一大段代码，或写较长说明。
         * 注意：多行注释不能嵌套。
         */

        // 若有命令行参数，可打印出来观察
        if (args.length > 0) {
            System.out.println("第一个参数: " + args[0]);
        }
    }
}
```

---

## 2. 数据类型与变量

### 为什么要讲

- 类型决定**内存大小**与**合法运算**；理解基本类型 vs 引用类型是后面集合、空指针的基础。

### 要点

- **8 种基本类型**：整型 `byte(1字节) short(2) int(4) long(8)`；浮点 `float(4) double(8)`；`char(2，Unicode)`；`boolean`（JVM 规范未规定字节数，通常 1 字节）。
- **字面量后缀**：`long` 用 `L`（小写 `l` 易与数字 1 混淆）；`float` 用 `f`。
- **引用类型**：变量里存的是**堆对象的地址**（引用）；未赋值引用默认 `null`。

### 易错点

- `double d = 10 / 3` 得到 `3.0`：因为 `10` 和 `3` 先做**整数除法**，再提升为 `double`。
- 强转 `(int)3.9` 是**截断**，不是四舍五入；四舍五入用 `Math.round` 等。

```java
/**
 * 演示：8 种基本类型、字面量、类型转换、引用类型变量。
 */
public class Demo02Types {
    public static void main(String[] args) {
        // ---------- 整型：注意范围，溢出会“绕圈”（补码），一般不依赖这种特性 ----------
        byte b = 127;   // byte 范围 -128 ~ 127
        short s = 32000;
        int i = 1_000_000;   // Java 7+：数字间可加下划线，仅增强可读性，编译器会忽略 _
        long l = 9_223_372_036_854_775_807L; // 超过 int 范围必须加 L，否则按 int 字面量处理会编译错误

        // ---------- 浮点：默认字面量是 double；float 必须加 f ----------
        float f = 3.14f;
        double d = 3.14;     // 或 3.14d

        // ---------- 字符与布尔 ----------
        char c = '中';       // 单引号表示一个 char；Unicode 字符
        boolean ok = true;   // 只有 true / false，不能与 0/1 互转

        // ---------- 类型转换 ----------
        // 强转：显式截断，小数部分直接丢掉
        int x = (int) 3.9;   // 结果为 3
        // 整数相除：结果仍是整数，小数被丢弃
        double y = 10 / 3;   // 先算 10/3 = 3，再转成 3.0
        double z = 10 / 3.0; // 或 10.0/3，参与运算的一方为 double，结果为 3.333...

        // ---------- 引用类型：String 是类，变量 s 保存的是对象引用 ----------
        String s = "引用类型"; // 字符串常量一般在字符串常量池中有一份复用（具体优化由 JVM 实现）
        String t = null;      // 引用可以指向“无对象”，后面若 t.xxx 会空指针异常
        if (t == null) {
            t = "";           // 空串与 null 不同：空串是长度为 0 的 String 对象
        }
    }
}
```

---

## 3. 运算符与流程控制

### 为什么要讲

- 业务逻辑都落在分支与循环上；`&&`/`||` 的短路行为常出现在面试与排错中。

### 要点

- **短路**：`a && b` 若 `a` 为 false，不再计算 `b`；`a || b` 若 `a` 为 true，不再计算 `b`。
- **`switch`**：Java 14+ 可用 `case 常量 -> 语句`；多分支返回值可用 `yield`（本示例用简单打印）。

### 易错点

- `=` 是赋值，`==` 是比较；`if (x = 1)` 在 Java 里通常编译不过（boolean 要求），但 `if (b = false)` 某些写法仍易混，习惯上把常量放左边：`if ("ok".equals(s))` 避免 NPE。

```java
/**
 * 演示：算术/关系/逻辑/三元运算符，if、switch、for、while、break、continue。
 */
public class Demo03Control {
    public static void main(String[] args) {
        int a = 5, b = 3;

        // 三元运算符：条件 ? 真时的值 : 假时的值（类型需能统一或提升）
        System.out.println(a > b ? "大" : "小");

        // 逻辑与 &&：左侧为 false 时，右侧表达式不会执行（短路）
        boolean f = false && (++a > 0);
        System.out.println(a); // 仍为 5，说明 ++a 没执行

        // if - else if - else：从上到下只进入一个分支
        if (a > 0) {
            System.out.println("正");
        } else if (a < 0) {
            System.out.println("负");
        } else {
            System.out.println("零");
        }

        // switch（Java 14+ 箭头语法）：无贯穿，更不易漏写 break
        int day = 2;
        switch (day) {
            case 1 -> System.out.println("Mon");
            case 2 -> System.out.println("Tue");
            default -> System.out.println("Other"); // 无匹配时走 default
        }

        // for：已知循环次数时常用
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                continue; // 跳过本次循环剩余部分，进入下一次 i++
            }
            System.out.println("i=" + i);
        }

        // while：条件在循环头，可能一次都不执行
        int n = 0;
        while (n < 2) {
            System.out.println("while n=" + n);
            n++;
        }

        // do-while：至少执行一次循环体
        n = 0;
        do {
            System.out.println("do-while n=" + n);
            n++;
        } while (n < 1); // 条件 false 时已执行过一次
    }
}
```

---

## 4. 数组

### 为什么要讲

- 数组是最基础的连续结构；后面 `ArrayList` 可理解为“可扩容的动态数组”。

### 要点

- 长度**创建后固定**；下标从 `0` 到 `length - 1`。
- **`length` 是数组的属性**，不是方法，不要写成 `length()`。
- 多维数组在 Java 中是**锯齿数组**：每一行长度可以不同。

### 易错点

- `int[] a = new int[3];` 默认初始化为 `0`；引用类型数组默认元素为 `null`。
- 越界访问抛出 `ArrayIndexOutOfBoundsException`。

```java
import java.util.Arrays;

/**
 * 演示：一维数组、多维数组、Arrays 工具类、增强 for。
 */
public class Demo04Array {
    public static void main(String[] args) {
        // 静态初始化：编译器根据元素个数确定长度
        int[] arr = {1, 2, 3};

        // 动态初始化：长度 3，元素默认为 0（int）
        int[] arr2 = new int[3];
        arr2[0] = 10;

        // length 是属性
        System.out.println("arr.length = " + arr.length);

        // Arrays.toString 便于打印，否则直接 print 数组得到的是类型@哈希
        System.out.println(Arrays.toString(arr));

        // 多维数组：每行可以是不同长度（锯齿数组）
        int[][] matrix = {
                {1, 2},
                {3, 4, 5}  // 第二行 3 个元素
        };
        // 增强 for：外层每个元素是一维 int[]
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        // 复制数组可用 Arrays.copyOf 或 System.arraycopy（性能场景再细讲）
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy); // 原地排序
        System.out.println("sorted: " + Arrays.toString(copy));
    }
}
```

---

## 5. 方法

### 为什么要讲

- 方法实现**复用**与**职责拆分**；理解重载与参数传递是读框架代码的前提。

### 要点

- **重载（Overload）**：同名方法，**参数个数或类型不同**；与返回值、参数名无关。
- **可变参数 `int...`**：调用时可传 0~N 个 int，方法体内当作 `int[]` 使用；必须放在参数列表**最后**，且只能有一个可变参数。
- **值传递**：基本类型拷贝**数值**；引用类型拷贝**引用的值**（地址副本），故可通过引用修改**同一对象内部**，但不能把调用处的引用改成指向另一个对象（见代码注释）。

### 易错点

- 仅返回值不同，不能构成重载（编译错误）。
- 可变参数与数组作为参数在重载解析时可能产生歧义，教学项目里尽量简单使用。

```java
/**
 * 演示：方法定义、重载、可变参数、Java 参数传递语义。
 */
public class Demo05Method {

    /** 两整数求和 */
    static int add(int a, int b) {
        return a + b;
    }

    /**
     * 重载：方法名相同，参数列表不同（三个 int）。
     * 注意：不能仅靠返回值不同来区分重载。
     */
    static int add(int a, int b, int c) {
        return a + b + c;
    }

    /**
     * 可变参数：nums 在方法内等价于 int[]。
     * 调用示例：sum()、sum(1)、sum(1,2,3)
     */
    static int sum(int... nums) {
        int s = 0;
        for (int n : nums) {
            s += n;
        }
        return s;
    }

    /**
     * 通过引用修改数组元素：调用方能看到 arr[0] 变化，
     * 因为修改的是堆上同一个数组对象。
     */
    static void bump(int[] arr) {
        if (arr != null && arr.length > 0) {
            arr[0]++;
        }
    }

    /**
     * 若写成 void swapRef(int[] arr) { arr = new int[]{9}; }，
     * 调用方外面的引用仍指向原数组，不会变成新数组——只是改了形参副本的指向。
     */
    static void tryReassign(int[] arr) {
        arr = new int[]{99}; // 只改变形参 arr 的指向，不影响 main 里的 a
    }

    public static void main(String[] args) {
        System.out.println(add(1, 2));
        System.out.println(sum(1, 2, 3, 4));

        int[] a = {0};
        bump(a);
        System.out.println("bump 后 a[0] = " + a[0]); // 输出 1

        tryReassign(a);
        System.out.println("tryReassign 后 a[0] = " + a[0]); // 仍是 1，不是 99
    }
}
```

---

## 6. 面向对象：封装

### 为什么要讲

- **封装**把数据与校验规则关在类内部，外部只能通过约定方法访问，便于维护和演进。

### 要点

- 字段一般 **`private`**，通过 **`public` getter/setter** 控制读写；setter 里做校验（年龄范围、非空等）。
- **`new Student()`** 在堆上创建对象，栈上的变量 `s` 保存引用。

### 易错点

- 若 setter 对非法值**静默忽略**，调用者可能不知道设置失败；教学可后续引入返回值或异常说明。

```java
/**
 * 学生实体：演示封装——隐藏字段，对外暴露受控的访问方式。
 */
public class Student {
    /** 姓名：不允许设为 null 或空白字符串 */
    private String name;
    /** 年龄：合理范围校验，避免荒诞数据 */
    private int age;

    /** 只读姓名时可只提供 getter，不提供 setter */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // isBlank：Java 11+，空白串也不接受；低版本可用 trim().isEmpty()
        if (name != null && !name.isBlank()) {
            this.name = name; // this 指“当前对象”，区分参数名与字段名
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0 && age < 150) {
            this.age = age;
        }
    }
}
```

```java
/**
 * 使用 Student：只能通过公开方法间接访问私有字段。
 */
public class Demo06Student {
    public static void main(String[] args) {
        Student s = new Student();
        s.setName("张三");
        s.setAge(20);
        // 不能直接 s.name = ... 若 name 为 private 会编译错误
        System.out.println(s.getName() + " " + s.getAge());
    }
}
```

---

## 7. 构造方法与 this / static

### 为什么要讲

- 构造方法负责**对象出生时的合法状态**；`this()` 避免重复代码；`static` 属于类，用于**与实例无关**的计数、工具方法。

### 要点

- 构造方法：**与类同名**、**无返回类型**（连 `void` 都不要写）。
- **`this(...)`** 调用本类其他构造：必须写在**当前构造的第一行**。
- **`static` 方法/字段**：属于类，通过 `类名.` 访问；静态方法里**不能**使用实例成员和 `this`（没有当前对象）。

### 易错点

- 在 static 方法里调用实例方法必须先有对象：`new Counter().getId()`。
- 下面示例中无参构造调用 `this(0)`，表示“默认 id 用 0”。

```java
/**
 * 演示：构造方法重载、this() 委托、static 类变量统计创建次数。
 */
public class Counter {
    /**
     * 类变量：所有 Counter 实例共享这一份存储。
     * 用于统计一共 new 了多少个 Counter。
     */
    static int totalCreated;

    /** 每个实例自己的编号 */
    private int id;

    /**
     * 无参构造：委托给带参构造，避免两套初始化逻辑重复。
     * this(0) 必须是本构造方法的第一条语句。
     */
    public Counter() {
        this(0);
    }

    /**
     * 带参构造：用调用方指定的 seed 作为 id，并增加全局计数。
     */
    public Counter(int seed) {
        this.id = seed;
        totalCreated++; // 每 new 一次执行一次
    }

    /** 静态方法：只能直接访问静态成员 */
    public static int getTotalCreated() {
        return totalCreated;
    }

    public int getId() {
        return id;
    }

    // 可在另一个类的 main 里测试：
    // new Counter(); new Counter(10);
    // System.out.println(Counter.getTotalCreated());
}
```

---

## 8. 继承与多态

### 为什么要讲

- **继承**复用父类代码；**多态**让“同一套调用代码”对不同子类产生不同行为，是框架里“面向接口/父类编程”的基础。

### 要点

- Java **单继承**：`class Dog extends Animal`。
- **方法重写**：子类 `@Override` 父类**实例方法**；子类访问权限不能比父类**更严格**（如父类 `protected`，子类不能改成 `private`）。
- **多态**：`Animal a = new Dog();` 编译期类型是 `Animal`，运行时若调用**重写后的** `speak()`，执行的是 `Dog` 的版本（动态绑定）。

### 易错点

- **字段没有多态**：通过 `Animal` 类型访问的若是字段，看的是**声明类型**一侧（隐藏规则，少用父类子类同名字段）。
- 构造子类时，会**先**链式调用父类构造（隐式或显式 `super(...)`）。

```java
/**
 * 父类：提供通用能力，子类可扩展或重写。
 */
class Animal {
    void speak() {
        System.out.println("...");
    }
}

class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("汪");
    }
}

class Cat extends Animal {
    @Override
    void speak() {
        System.out.println("喵");
    }
}

/**
 * 多态：方法参数类型为父类，传入任意子类对象均可。
 */
public class Demo07Poly {

    /**
     * 编译期：只知道 a 是 Animal，能调用 Animal 上声明的实例方法。
     * 运行期：a 实际指向 Dog/Cat，调用各自重写的 speak()。
     */
    static void hear(Animal a) {
        a.speak();
    }

    public static void main(String[] args) {
        Animal d = new Dog(); // 向上转型：子类引用赋给父类变量，安全
        Animal c = new Cat();
        hear(d);
        hear(c);

        // 向下转型：必须先确定实际类型，否则 ClassCastException
        if (d instanceof Dog dog) { // Java 16+ pattern matching
            dog.speak();
        }
    }
}
```

> **说明**：若 JDK 低于 16，`instanceof` 需写成：
> `if (d instanceof Dog) { ((Dog) d).speak(); }`

---

## 9. 抽象类与接口

### 为什么要讲

- **抽象类**：模板方法、部分实现复用；**接口**：定义能力契约，实现类可多个 `implements`。

### 要点

- 抽象类可包含**抽象方法**（无方法体）和**具体方法**；不能直接 `new` 抽象类，除非匿名子类。
- 接口在 Java 8+ 可有 **`default`** 默认实现（实现类可不重写）和 **`static`** 工具方法。
- **面向接口编程**：方法参数、字段类型尽量写接口（如 `List`），实现可换 `ArrayList`/`LinkedList`。

### 易错点

- 接口里的实例方法默认 **`public abstract`**（可省略修饰符）；实现类重写时必须 `public`（不能缩小权限）。

```java
/**
 * 抽象类：表达“是一种形状”，但面积算法由子类完成。
 */
abstract class Shape {
    /** 抽象方法：没有方法体，强制子类实现 */
    abstract double area();
}

class Circle extends Shape {
    private final double r;

    Circle(double r) {
        this.r = r;
    }

    @Override
    double area() {
        return Math.PI * r * r;
    }
}

/**
 * 接口：表达“具备飞行能力”，与继承树无关，可多实现。
 */
interface Flyable {
    void fly();

    /**
     * default：实现类自动拥有，也可选择重写。
     * 用于在接口演进时给老实现类默认行为，避免全部编译失败。
     */
    default void glide() {
        System.out.println("滑翔");
    }
}

class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("鸟在飞");
    }
}
```

---

## 10. 枚举与包

### 为什么要讲

- 枚举比 `public static final int` **类型更安全**，且可附带属性与方法。
- **包**解决命名冲突，并与访问权限配合（默认包访问、protected 等）。

### 要点

- 枚举常量一般**全大写**；构造方法默认 `private`（不允许外部 new 枚举实例）。
- `Season.values()` 得到所有常量数组；`name()` 得到常量名字符串。

### 易错点

- 枚举比较优先用 `==`（同一 JVM 中单例），也可用 `equals`。

```java
/**
 * 季节枚举：每个枚举常量可携带中文说明等附加数据。
 */
public enum Season {
    // 枚举常量列表必须写在最前；末尾分号后可写字段、方法
    SPRING("春"),
    SUMMER("夏");

    /** 每个常量一份，用 final 保证不可变 */
    private final String cn;

    /**
     * 枚举构造默认 private；仅能在枚举类内部创建常量时调用。
     */
    Season(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }
}
```

```java
/**
 * 使用枚举：switch、遍历、name()。
 */
public class Demo08Enum {
    public static void main(String[] args) {
        Season s = Season.SPRING;
        // name() 返回声明时的标识符，如 "SPRING"
        System.out.println(s.name() + " " + s.getCn());

        for (Season x : Season.values()) {
            System.out.println(x);
        }

        // switch 可直接用枚举（Java 5+）
        switch (s) {
            case SPRING -> System.out.println("春天");
            case SUMMER -> System.out.println("夏天");
        }
    }
}
```

**包与 import（说明）**

```java
// 文件第一行非空语句应是 package（若使用包）
// package com.example.basics;
//
// import java.util.List;  // 导入单个类
// import java.util.*;     // 导入包下类型（不推荐滥用，可读性差）
```

---

## 11. 常用类：String 与 Object

### 为什么要讲

- `String` 日常最多；理解**不可变**才能理解拼接性能与常量池。
- 对象放入 **`HashMap`/`HashSet`** 时，`equals` 与 `hashCode` **必须一致重写**。

### 要点

- `String` 对象内容不可变，每次“修改”往往产生新对象；循环内大量 `+` 会生成很多中间对象，应用 **`StringBuilder`**。
- **`equals` 约定**：自反、对称、传递、一致；与 `hashCode` 一致：相等对象必须有相同哈希。

### 易错点

- 用 `==` 比较字符串内容常常错误；应使用 **`"常量".equals(变量)`** 避免 NPE。

```java
import java.util.Objects;

/**
 * 演示：重写 equals / hashCode / toString 的标准写法。
 */
public class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // 同一引用，一定相等
        }
        if (!(o instanceof Point p)) {
            return false;
        }
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        // 与 equals 一致：相等的点哈希必须相同
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point(" + x + "," + y + ")";
    }
}
```

```java
/**
 * String 与 StringBuilder、strip 等 API 示例。
 */
public class Demo09String {
    public static void main(String[] args) {
        String a = "ab";
        String b = "a" + "b";
        // 编译器可能把 "a"+"b" 优化为 "ab"，与 a 同指向常量池 → == 可能为 true
        // 依赖优化行为写业务逻辑不可靠，比较内容请用 equals
        System.out.println(a.equals(b)); // 应优先用这个判断内容相等
        System.out.println(a == b);      // 可能 true，不要依赖

        // 循环拼接：用 StringBuilder 减少临时 String 对象
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(i);
        }
        System.out.println(sb.toString());

        // strip：去掉首尾空白（Unicode 意义下的空白）；Java 11+
        String s = "  hello  ";
        System.out.println(s.strip());
    }
}
```

---

## 12. 包装类与自动装箱

### 为什么要讲

- 集合等 API 需要**对象类型**，基本类型通过包装类接入；理解装箱拆箱与缓存避免诡异 bug。

### 要点

- 对应关系：`int→Integer`、`long→Long`、`double→Double`、`boolean→Boolean` 等。
- **自动装箱**本质是 `Integer.valueOf(x)`；**`-128~127`** 范围内 `valueOf` 常返回**缓存**的同一对象，故 `==` 可能为 true；超出范围通常是不同对象，`==` 为 false。

### 易错点

- **`Integer` 与 `int` 混合运算**会拆箱；若 `Integer` 为 `null`，拆箱抛 `NullPointerException`。

```java
/**
 * 演示：装箱、拆箱、缓存区间、应用 equals 比较值。
 */
public class Demo10Boxing {
    public static void main(String[] args) {
        // 自动装箱：编译器转为 Integer.valueOf(100)
        Integer a = 100;
        Integer b = 100;
        // 在缓存范围内，valueOf 可能返回同一实例 → == 为 true（仍建议用 equals 比较值）
        System.out.println(a == b);

        Integer c = 200;
        Integer d = 200;
        // 超出默认缓存，往往是两个不同对象
        System.out.println(c == d);     // false
        System.out.println(c.equals(d)); // true，比较值应这样写

        // 自动拆箱：Integer 转为 int，相当于 c.intValue()
        int x = c;
        Integer y = x; // 再装箱

        // 危险示例：Integer 可能为 null，参与运算前需判空
        Integer maybe = null;
        // int bad = maybe; // 运行期 NPE
    }
}
```

---

## 13. 异常

### 为什么要讲

- 异常是 Java 的错误处理机制；**受检异常**强迫调用方处理或声明，提高健壮性。

### 要点

- **`try-catch-finally`**：`finally` 一般用于释放资源（无论是否异常都会执行，除非 JVM 退出）。
- **`try-with-resources`**：资源类实现 `AutoCloseable`，离开 try 块自动 `close()`，代码更简洁。
- **受检异常**：继承 `Exception` 且非 `RuntimeException`，方法须 `throws` 或内部捕获；**非受检**：`RuntimeException` 及其子类。

### 易错点

- 空 `catch` 吞掉异常，线上难以排查；至少打日志。
- `catch` 子类异常须写在父类**前面**，否则不可达编译错误。

```java
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * 演示：捕获异常、try-with-resources。
 */
public class Demo11Exception {

    /** 可能抛出非受检异常 NumberFormatException */
    static int parse(String s) {
        return Integer.parseInt(s);
    }

    /** 受检异常示例：调用方必须处理或继续 throws */
    static void mightFail() throws java.io.IOException {
        throw new java.io.IOException("演示");
    }

    public static void main(String[] args) {
        try {
            System.out.println(parse("abc"));
        } catch (NumberFormatException e) {
            // 至少记录消息；生产环境用日志框架
            System.err.println("解析失败: " + e.getMessage());
        }

        // try-with-resources：br 会自动 close，即使 readLine 抛异常
        try (BufferedReader br = new BufferedReader(new StringReader("line"))) {
            System.out.println(br.readLine());
        } catch (Exception e) {
            e.printStackTrace(); // 学习阶段可用；生产慎用
        }

        try {
            mightFail();
        } catch (java.io.IOException e) {
            System.err.println("IO: " + e.getMessage());
        }
    }
}
```

---

## 14. 泛型入门

### 为什么要讲

- 泛型让集合在**编译期**检查类型，减少强制转换与 `ClassCastException`；企业代码中泛型无处不在。

### 要点

- **类型参数** `T` 在编译后**擦除**为 `Object` 或上界，运行时看不到 `T` 的具体类型（反射另议）。
- **通配符**：`? extends Number` 适合**只读**生产者；`? super Integer` 适合**只写**消费者（PECS 原则）。

### 易错点

- 不能 `new T()`；不能创建 `new T[]`（数组与泛型擦除冲突）。

```java
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类：盒子内存放类型由调用方指定，编译期检查 set/get 类型一致。
 */
class Box<T> {
    private T value;

    void set(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }
}

/**
 * 泛型方法：<T> 声明在返回类型前，与类泛型独立。
 */
public class Demo12Generic {

    static <T> void printFirst(List<T> list) {
        if (!list.isEmpty()) {
            System.out.println(list.get(0));
        }
    }

    public static void main(String[] args) {
        Box<String> b = new Box<>();
        b.set("hi");
        // b.set(1); // 编译错误：Box<String> 不能接受 int
        System.out.println(b.get());

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        printFirst(nums);
    }
}
```

---

## 15. 集合框架

### 为什么要讲

- 实际开发几乎不手写动态数组；**选对集合**影响正确性与性能。

### 要点

| 接口 | 特点 | 常见实现 |
|------|------|----------|
| `List` | 有序、可重复、有下标 | `ArrayList`、`LinkedList` |
| `Set` | 不重复 | `HashSet`、`LinkedHashSet`、`TreeSet` |
| `Map` | 键值对，键唯一 | `HashMap`、`LinkedHashMap`、`TreeMap` |

### 易错点

- 遍历 `Map` 同时**结构性修改**（非 Iterator.remove）可能 `ConcurrentModificationException`。
- `HashMap` 的键若可变对象且参与 hash 的字段被改，会破坏结构（键建议不可变）。

```java
import java.util.*;

/**
 * 演示：List / Set / Map 常用 API 与遍历方式。
 */
public class Demo13Collection {
    public static void main(String[] args) {
        // List：有序，可重复
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a"); // 允许重复
        // forEach 接受 Consumer，方法引用 System.out::println 等价于 s -> System.out.println(s)
        list.forEach(System.out::println);

        // Set：不重复，HashSet 不保证顺序
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1); // 第二次 add 无效
        System.out.println("set.size = " + set.size()); // 1

        // Map：键唯一；put 相同键会覆盖旧值
        Map<String, Integer> map = new HashMap<>();
        map.put("age", 20);
        map.putIfAbsent("age", 99); // 已有 age，不覆盖
        System.out.println(map.get("age")); // 20

        // 遍历 entrySet：同时需要键和值时推荐
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + "=" + e.getValue());
        }

        // 仅遍历键或值
        for (String k : map.keySet()) {
            System.out.println(k);
        }
    }
}
```

---

## 16. Lambda 与 Stream 入门

### 为什么要讲

- Java 8 后集合处理更简洁；Stream 适合**声明式**处理流水线，注意**惰性**与**一次消费**。

### 要点

- **函数式接口**：只有一个抽象方法的接口，如 `Runnable`、`Comparator<T>`、`Predicate<T>`。
- **Lambda**：`(参数) -> { 体 }`，单表达式可省略大括号与 return。
- **Stream**：`stream()` 得到流；**中间操作**（lazy，不立即执行）如 `filter`、`map`；**终端操作**触发计算，如 `collect`、`forEach`、`count`。

### 易错点

- Stream 流水线执行完**不能再复用**，需要再 `stream()`。
- 并行流 `parallelStream()` 需保证线程安全与合理分片，初学少用。

```java
import java.util.List;
import java.util.stream.Collectors;

/**
 * 需要 Java 9+（List.of）。Java 8 见下一代码块。
 */
public class Demo14Stream {
    public static void main(String[] args) {
        // Runnable 是函数式接口：只有一个 run()
        Runnable r = () -> System.out.println("run");
        r.run();

        // 不可变列表（Java 9+）
        List<Integer> nums = List.of(1, 2, 3, 4, 5);
        // filter / map 为中间操作，惰性；collect 为终端操作，真正执行流水线
        List<Integer> evens = nums.stream()
                .filter(n -> n % 2 == 0)   // 保留偶数
                .map(n -> n * 10)          // 每个元素映射为新值
                .collect(Collectors.toList());
        System.out.println(evens); // [20, 40]
    }
}
```

> **注意**：`List.of` 需要 Java 9+。教学环境为 Java 8 时用 `Arrays.asList`。

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java 8 兼容：Arrays.asList 得到的是固定大小列表，不能 add/remove，
 * 但元素可替换（与 List.of 的不可变不同）；仅作 Stream 数据源足够。
 */
public class Demo14StreamJava8 {
    public static void main(String[] args) {
        Runnable r = () -> System.out.println("run");
        r.run();

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> evens = nums.stream()
                .filter(n -> {
                    // 多行 lambda：需要显式 return
                    return n % 2 == 0;
                })
                .map(n -> n * 10)
                .collect(Collectors.toList());
        System.out.println(evens);
    }
}
```

---

## 17. 串讲脉络小结

### 一条线（记忆版）

```
环境 → main → 类型与变量 → 运算与流程 → 数组与方法
    → 封装 / 继承 / 多态 → 抽象类与接口 → String / Object / 包装类 / 枚举
    → 异常 → 泛型 → 集合 → Lambda / Stream（入门）
```

### 课堂可强调的三句话

1. **基本类型存值，引用类型存引用**；方法参数永远是值传递；改对象内容要通过引用找到堆上同一对象。  
2. **多态**：编译看**左边**声明类型能调用哪些方法；**实例方法**运行看**右边**实际对象的重写版本。  
3. **集合选型**：随机访问多 → `ArrayList`；键值检索 → `Map`；去重 → `Set`；要排序 → `TreeSet`/`TreeMap` 或 Stream `sorted`。

### 与下一阶段的衔接

- **并发**：`synchronized`、线程池、`ConcurrentHashMap`。  
- **IO / NIO**：文件与网络（再往后 Spring Web）。  
- **JVM**：内存区域、GC 概念，为排错打基础。

---

## 附录：文件组织建议（教学用）

- 每个 `public class DemoXX` 单独一个 `.java` 文件，包名如 `com.qdu.basics`。  
- `Student`、`Point`、`Animal` 等可与 Demo 同包或分子包 `model`。  
- IDEA：右键类文件 → Run，或点击 `main` 旁绿色三角。

---

*文档版本：详解版 — 与课程《Java 基础串讲》配套，可按周教学操作手册调整节次。*
