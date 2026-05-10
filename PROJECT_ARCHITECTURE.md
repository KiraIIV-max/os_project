# 📋 خريطة مفصلة لمشروع SJF vs Priority Scheduler

---

## 🏗️ البنية العامة للمشروع

```
SJF vs Priority Project
│
├── 📦 src/model/           [نموذج البيانات]
│   └── Process.java        (تمثيل العملية الواحدة)
│
├── 📦 src/scheduler/       [محركات التخطيط]
│   ├── BaseScheduler.java  (الفئة الأساسية)
│   ├── SJFScheduler.java   (أقصر وقت أولاً - Preemptive)
│   └── PriorityScheduler.java (حسب الأولوية)
│
├── 📦 src/utils/           [أدوات مساعدة]
│   ├── Validator.java      (التحقق من صحة المدخلات)
│   └── MetricsCalculator.java (حساب المقاييس)
│
├── 📦 src/ui/              [واجهة المستخدم - الرسومات]
│   ├── App.java            (نقطة البداية الرئيسية)
│   ├── UITheme.java        (الألوان والخطوط والمسافات)
│   ├── GanttChart.java     (رسم الجدول الزمني)
│   └── ResultTable.java    (عرض جدول النتائج - غير مستخدم حالياً)
│
├── 📦 src/views/           [الشاشات]
│   ├── InputScene.java     (شاشة إدخال البيانات)
│   └── ResultScene.java    (شاشة النتائج)
│
└── 📦 src/components/      [مكونات الواجهة]
    ├── atoms/              (عناصر أساسية)
    │   ├── ActionButton.java
    │   ├── Input.java
    │   ├── Card.java
    │   ├── Badge.java
    │   ├── Alert.java
    │   ├── Divider.java
    │   ├── PillButton.java
    │   └── Table.java
    │
    ├── molecules/          (مكونات مركبة)
    │   ├── ProcessForm.java (نموذج إضافة عملية)
    │   ├── BreakdownTable.java (جدول التفاصيل)
    │   ├── MetricCard.java (بطاقة المقياس)
    │   ├── ScenarioBar.java (أزرار السيناريوهات)
    │   ├── ComparisonSummary.java (ملخص المقارنة)
    │   └── ConclusionPanel.java (لوحة الخلاصة)
    │
    └── organisms/          (مكونات معقدة)
        └── ProcessTablePanel.java (لوحة جدول العمليات)
```

---

## 📊 تدفق البيانات الرئيسي (Data Flow)

```
[المستخدم]
    ↓
[InputScene - شاشة الإدخال]
    ↓ (يدخل: PID, Arrival Time, Burst Time, Priority)
[Validator - التحقق من الصحة]
    ↓ (يرفع الأخطاء إذا كانت البيانات خاطئة)
[Process[] - قائمة العمليات]
    ↓ (نسخة لـ SRTF)  (نسخة لـ Priority)
    ↓                  ↓
[SJFScheduler]     [PriorityScheduler]
    ↓                  ↓
[BaseScheduler.schedule()]
    ↓                  ↓
[Gantt Log - قائمة PIDs] [Gantt Log]
    ↓                  ↓
[Process[] - معروض]  [Process[] - معروض]
    (مع القيم المحسوبة)
    ↓
[MetricsCalculator - حساب المقاييس]
    ↓
[ResultScene - شاشة النتائج]
    ├── [MetricCard] - بطاقات المقاييس
    ├── [BreakdownTable] - جداول التفاصيل
    ├── [GanttChart] - الرسوم البيانية
    ├── [ComparisonSummary] - ملخص المقارنة
    └── [ConclusionPanel] - الخلاصة والتوصيات
```

---

## 📄 شرح تفصيلي ملف بملف

---

### 1️⃣ **src/model/Process.java** - نموذج العملية

**الغرض:** تمثيل عملية واحدة برسمتها ومعلومات تنفيذها

**المتغيرات (Variables):**
```
pid              → معرف العملية (1, 2, 3...)
arrivalTime      → وقت وصول العملية (بالـ ms)
burstTime        → وقت تنفيذ العملية (بالـ ms)
priority         → رقم الأولوية (1 = أعلى، رقم أكبر = أقل)
remainingTime    → الوقت المتبقي (يتناقص أثناء التنفيذ)
completionTime   → وقت الانتهاء من التنفيذ
waitingTime      → وقت الانتظار = TAT - BurstTime
turnaroundTime   → الوقت الكلي = CompletionTime - ArrivalTime
responseTime     → وقت الاستجابة = FirstRunTime - ArrivalTime
started          → هل بدأ التنفيذ؟
firstRunTime     → أول مرة يعمل فيها (لحساب RT)
```

**الدوال الرئيسية:**
| الدالة | المدخلات | المخرجات | الوظيفة |
|--------|---------|---------|--------|
| `Process(pid, arrival, burst, priority)` | 4 أرقام | كائن Process | إنشاء عملية جديدة |
| `reset()` | - | - | إعادة تعيين جميع القيم (لتشغيل محاكاة ثانية) |
| `getX()` / `setX()` | - | قيمة / - | الحصول على أو تعيين قيمة |

**الشروط المطلوبة:**
- ✓ `pid` > 0
- ✓ `arrivalTime` ≥ 0
- ✓ `burstTime` > 0
- ✓ `priority` ≥ 1
- ✓ يجب ألا يكون هناك `pid` مكرر

**مثال:**
```java
Process p = new Process(1, 0, 5, 2);
// العملية رقم 1، تصل في الوقت 0، تحتاج 5 ميلي ثانية، الأولوية 2
```

---

### 2️⃣ **src/scheduler/BaseScheduler.java** - محرك التخطيط الأساسي

**الغرض:** تنفيذ خوارزمية التخطيط العامة (تكون مختلفة بين SJF و Priority)

**دالة رئيسية واحدة:**

#### `List<Integer> schedule(List<Process> processes)`

**المدخلات:**
- `processes`: قائمة العمليات (يجب ألا تكون null)

**المخرجات:**
- `List<Integer>`: قائمة الـ PIDs في ترتيب التنفيذ (gantt log)
  - `-1` = وقت خامل (لا توجد عملية تعمل)
  - `1, 2, 3...` = معرف العملية التي تعمل

**خطوات التنفيذ:**

```
1. إعادة تعيين جميع العمليات → reset()
2. إنشاء قائمة ganttLog فارغة
3. بينما (لم تنته جميع العمليات):
   a. استدعاء selectNext() → اختيار العملية التالية المراد تنفيذها
   b. إذا لا توجد عملية:
      - أضف -1 (وقت خامل)
   c. إذا كانت هذه أول مرة للعملية:
      - وسّم كـ "بدأت"
      - احفظ firstRunTime = currentTime
   d. انقص remainingTime بمقدار 1
   e. أضف PID إلى ganttLog
   f. إذا انتهت العملية (remainingTime = 0):
      - احفظ completionTime = currentTime + 1
   g. زد currentTime بمقدار 1
4. احسب المقاييس لكل عملية:
   - TAT = CompletionTime - ArrivalTime
   - WT = TAT - BurstTime
   - RT = FirstRunTime - ArrivalTime
5. أرجع ganttLog
```

**مثال Gantt Log:**
```
Input:  Process 1 (arrival=0, burst=3), Process 2 (arrival=1, burst=2)
Output: [1, 1, 1, 2, 2]
Timeline: T=0→1→2→3→4→5
         P1  P1  P1  P2  P2
```

**دالة مساعدة:**
```java
private int calculateMaxTime(List<Process> processes)
// تحسب أقصى وقت ممكن = max(Arrival) + sum(Burst)
```

---

### 3️⃣ **src/scheduler/SJFScheduler.java** - أقصر وقت أولاً (Preemptive)

**الغرض:** تنفيذ خوارزمية SRTF (Shortest Remaining Time First)

**دالة واحدة:**

#### `Process selectNext(List<Process> processes, int currentTime)`

**المدخلات:**
- `processes`: قائمة جميع العمليات
- `currentTime`: الوقت الحالي

**المخرجات:**
- العملية التي يجب تنفيذها الآن (أو null إذا لا توجد)

**المنطق:**
```
1. ابحث عن جميع العمليات التي:
   ✓ وصلت بالفعل (arrivalTime <= currentTime)
   ✓ لم تنته بعد (remainingTime > 0)

2. من هذه العمليات، اختر:
   ✓ التي لديها أقل remainingTime
   ✓ إذا تساوت اثنتان، اختر التي وصلت أولاً
```

**مثال:**
```
currentTime = 5
العمليات المتاحة:
  P1: arrival=0, remaining=2
  P2: arrival=1, remaining=3
  P3: arrival=5, remaining=1

النتيجة: P1 (لديها أقل remaining time = 2)
```

---

### 4️⃣ **src/scheduler/PriorityScheduler.java** - حسب الأولوية

**الغرض:** تنفيذ خوارزمية Priority Scheduling

**دالة واحدة:**

#### `Process selectNext(List<Process> processes, int currentTime)`

**المدخلات:**
- `processes`: قائمة جميع العمليات
- `currentTime`: الوقت الحالي

**المخرجات:**
- العملية التي يجب تنفيذها الآن

**المنطق:**
```
1. ابحث عن جميع العمليات التي:
   ✓ وصلت بالفعل (arrivalTime <= currentTime)
   ✓ لم تنته بعد (remainingTime > 0)

2. من هذه العمليات، اختر:
   ✓ التي لديها أقل priority (1 أفضل من 2)
   ✓ إذا تساوت الأولويات، اختر التي وصلت أولاً
```

**مثال:**
```
currentTime = 5
العمليات المتاحة:
  P1: arrival=0, priority=2, remaining=2
  P2: arrival=1, priority=1, remaining=3
  P3: arrival=5, priority=3, remaining=1

النتيجة: P2 (لديها priority=1، وهي الأعلى)
```

**الفرق من SJF:**
- SJF: ينظر لـ `remainingTime`
- Priority: ينظر لـ `priority`

---

### 5️⃣ **src/utils/Validator.java** - التحقق من الصحة

**الغرض:** التحقق من صحة البيانات المدخلة من المستخدم

**المتغيرات:**
```java
private HashSet<Integer> usedPIDs  // قائمة الـ PIDs المستخدمة بالفعل
```

**الدوال الرئيسية:**

#### `boolean isValidProcess(String pidStr, String arrivalStr, String burstStr, String priorityStr)`

**المدخلات:** 4 نصوص (Strings) من نماذج الإدخال

**المخرجات:** 
- `true` = البيانات صحيحة ✓
- `false` = البيانات خاطئة ✗

**الشروط:**
```
1. جميع المدخلات يجب أن تكون أرقام صحيحة
   ✗ "abc" → فشل
   ✓ "123" → نجاح

2. arrival ≥ 0 (لا يمكن أن تصل قبل الوقت 0)
   ✗ "-1" → فشل
   ✓ "0" → نجاح

3. burst > 0 (يجب أن تحتاج وقت تنفيذ موجب)
   ✗ "0" → فشل
   ✓ "5" → نجاح

4. priority ≥ 1 (الأولوية الدنيا 1)
   ✗ "0" → فشل
   ✓ "1" → نجاح

5. pid لم يُستخدم من قبل
   ✗ إذا كان "1" مستخدماً بالفعل → فشل
   ✓ إذا كان جديداً → نجاح
```

#### `String getErrorMessage(...)`

**المدخلات:** نفس المدخلات السابقة

**المخرجات:** نص الخطأ (أو null إذا صحيح)

```
"Invalid input: must be numbers only"     → إذا لم يكن رقم
"Arrival time must be >= 0"                → إذا arrival < 0
"Burst time must be > 0"                   → إذا burst ≤ 0
"Priority must be >= 1"                    → إذا priority < 1
"PID already exists"                       → إذا pid مكرر
null                                       → البيانات صحيحة
```

#### `void reset()` و `void removePID(int pid)`

```
reset()      → مسح قائمة الـ PIDs المستخدمة (عند مسح الشاشة)
removePID()  → إزالة PID واحد (عند حذف عملية)
```

---

### 6️⃣ **src/utils/MetricsCalculator.java** - حساب المقاييس

**الغرض:** حساب جميع مقاييس الأداء

**الدوال:**

#### حساب المقاييس الأساسية (بعد التخطيط):

```java
public static void computeMetrics(List<Process> processes)
```

يحسب لكل عملية:
- `TAT = CompletionTime - ArrivalTime` (الوقت من الوصول للانتهاء)
- `WT = TAT - BurstTime` (وقت الانتظار البحت)
- `RT = FirstRunTime - ArrivalTime` (الوقت من الوصول لبداية التنفيذ)

**مثال:**
```
العملية:
  arrival = 0
  burst = 5
  firstRunTime = 0
  completionTime = 5

الحساب:
  TAT = 5 - 0 = 5
  WT = 5 - 5 = 0
  RT = 0 - 0 = 0
```

#### حساب المتوسطات:

```java
public static double avgWaitingTime(List<Process> processes)
public static double avgTurnaroundTime(List<Process> processes)
public static double avgResponseTime(List<Process> processes)
```

**المخرجات:** المتوسط الحسابي (double)

```
مثال:
  العمليات: [P1(WT=2), P2(WT=3), P3(WT=1)]
  النتيجة: (2+3+1)/3 = 2.0 ms
```

#### تحليل الإنصاف والجوعان (Starvation):

```java
public static int getMaxWaitingTime(List<Process> processes)
public static int getMinWaitingTime(List<Process> processes)
public static boolean hasStarvation(List<Process> processes)
```

```
maxWait = أطول وقت انتظار
minWait = أقصر وقت انتظار
hasStarvation = maxWait > 50 (إذا تجاوز 50ms → جوعان)
```

---

### 7️⃣ **src/ui/App.java** - نقطة البداية

**الغرض:** تشغيل التطبيق وإدارة التنقل بين الشاشات

**الدوال:**

#### `void start(Stage stage)`

**خطوات التنفيذ:**

```
1. أنشئ InputScene (شاشة الإدخال)
2. عند النقر على "Begin Simulation":
   a. انسخ قائمة العمليات مرتين (لـ SRTF و Priority)
   b. شغّل SJFScheduler.schedule() → احصل على SRTF Gantt Log
   c. شغّل PriorityScheduler.schedule() → احصل على Priority Gantt Log
   d. أنشئ ResultScene (شاشة النتائج) مع البيانات
   e. غيّر الشاشة إلى النتائج
3. عند النقر على "Back" في النتائج:
   a. غيّر الشاشة إلى InputScene
```

#### `ArrayList<Process> deepCopy(ArrayList<Process> original)`

**المدخلات:** قائمة العمليات

**المخرجات:** نسخة جديدة من القائمة (كل عملية مستقلة)

**السبب:** لتشغيل محاكاة مختلفة لكل خوارزمية دون تأثر أحدهما بالأخرى

---

### 8️⃣ **src/views/InputScene.java** - شاشة الإدخال

**الغرض:** واجهة إدخال العمليات من المستخدم

**المكونات:**
- `ProcessForm` → نموذج إضافة عملية (PID, Arrival, Burst, Priority)
- `ProcessTablePanel` → جدول العمليات المضافة
- `ScenarioBar` → أزرار السيناريوهات الجاهزة
- أزرار: "Begin Simulation" و "Clear"

**الدوال:**

#### `VBox render()`

ترسم الشاشة بجميع مكوناتها

#### `void loadScenario(int[][] processData)`

**المدخلات:**
```
int[][] = {
  {pid, arrival, burst, priority},
  {pid, arrival, burst, priority},
  ...
}
```

**التنفيذ:**
```
1. امسح جميع العمليات الحالية
2. امسح قائمة الـ PIDs المستخدمة
3. أضف جميع العمليات من السيناريو
4. حدّث الجدول
```

#### `void handleAddProcess(String[] fields)`

**المدخلات:** مصفوفة [pidStr, arrivalStr, burstStr, priorityStr]

**التنفيذ:**
```
1. تحقق من الصحة باستخدام Validator
2. إذا كانت البيانات خاطئة:
   - عرض رسالة خطأ
   - لا تضف العملية
3. إذا كانت صحيحة:
   - أنشئ Process جديد
   - أضفه لقائمة العمليات
   - امسح حقول الإدخال
   - حدّث الجدول
```

#### `void showValidationDemo()`

**الغرض:** عرض تجربة التحقق من الصحة

```
1. أضف عمليتين صحيحتين
2. عرض رسالة توضح أنواع الأخطاء المحتملة
```

---

### 9️⃣ **src/views/ResultScene.java** - شاشة النتائج

**الغرض:** عرض نتائج المحاكاة والمقارنة

**المدخلات:**
```java
List<Process> srtfProcesses       // العمليات بعد SRTF
List<Integer> srtfGanttLog        // تسلسل التنفيذ لـ SRTF
List<Process> priorityProcesses   // العمليات بعد Priority
List<Integer> priorityGanttLog    // تسلسل التنفيذ لـ Priority
Runnable onBack                   // ما يحدث عند النقر "Back"
```

**المكونات:**
1. **MetricCards** (3 بطاقات):
   - Avg. Waiting Time
   - Avg. Turnaround Time
   - Avg. Response Time
   - كل واحدة تظهر قيمة SRTF و Priority مع Winner badge

2. **BreakdownTables** (جدولان):
   - جدول SRTF: PID, WT, TAT, RT
   - جدول Priority: PID, WT, TAT, RT

3. **GanttCharts** (رسمان):
   - رسم SRTF: تصور التسلسل الزمني
   - رسم Priority: تصور التسلسل الزمني

4. **ComparisonSummary**:
   - Winner لكل مقياس
   - تحليل الإنصاف
   - كشف الجوعان

5. **ConclusionPanel**:
   - تحليل شامل
   - توصيات
   - مقارنة الكفاءة مقابل الأولوية

---

### 🔟 **src/components/atoms/Input.java** - حقل الإدخال

**الغرض:** حقل نص يدخل فيه المستخدم البيانات

**المظهر:**
```
┌─ Label (PID, Arrival Time, إلخ)
└─ TextField (حقل النص)
```

**الدوال:**
```java
getText()      → الحصول على النص المدخل
clear()        → مسح الحقل
getNode()      → الحصول على الـ UI component
```

---

### 1️⃣1️⃣ **src/components/molecules/ProcessForm.java** - نموذج الإدخال

**المكونات:**
```
[PID Input] [Arrival Input] [Burst Input] [Priority Input] [Add Button]
```

**المدخلات:** callback عند النقر "Add"

**المخرجات:** مصفوفة String من القيم الأربعة

---

### 1️⃣2️⃣ **src/components/molecules/BreakdownTable.java** - جدول التفاصيل

**المخرجات:**
```
┌─ Card with Title (SRTF Breakdown / Priority Breakdown)
│  ┌─ Row Headers: PID | Waiting Time | Turnaround Time | Response Time
│  ├─ Row Data 1: P1  | 2            | 5               | 0
│  ├─ Row Data 2: P2  | 3            | 6               | 1
│  └─ Row Data 3: P3  | 1            | 4               | 0
```

---

### 1️⃣3️⃣ **src/components/molecules/MetricCard.java** - بطاقة المقياس

**المظهر:**
```
┌─ Metric Card Title (Avg. Waiting Time)
├─ SRTF: 2.0 ms [Winner]
└─ Priority: 2.5 ms
```

**المنطق:**
- إذا SRTF < Priority → أضف "Winner" badge لـ SRTF
- إذا Priority < SRTF → أضف "Winner" badge لـ Priority

---

### 1️⃣4️⃣ **src/ui/GanttChart.java** - رسم الجدول الزمني

**المخرجات:** Canvas رسم

**المدخلات:**
```java
List<Integer> ganttLog  // [1, 1, 1, 2, 2, -1, 3]
```

**الرسم:**
```
Time: 0    1    2    3    4    5    6    7
     ┌─┬─┬─┬─┬─┬────┬─┐
     │P1  │ │P2    │ │P3│
     └─┴─┴─┴─┴─┴────┴─┘
      Busy Busy Busy Idle Process
```

**الألوان:**
- عملية مختلفة = لون مختلف من الـ Palette
- وقت خامل (-1) = لون رمادي

---

## 🔄 مثال عملي شامل

### السيناريو:
```
العمليات:
  P1: arrival=0, burst=3, priority=2
  P2: arrival=1, burst=2, priority=1
  P3: arrival=2, burst=1, priority=3
```

### محاكاة SRTF (Preemptive):

| الوقت | العمليات المتاحة | يختار | الحدث |
|------|------------------|------|-------|
| 0 | P1 | P1 | P1 يبدأ (RT=0-0=0) |
| 1 | P1(R=2), P2(R=2) | P1 (متساوية، جاءت أولاً) | P1 يستمر |
| 2 | P1(R=1), P2(R=2), P3(R=1) | P1 أو P3 (R=1، لكن P1 وصل أولاً) | P1 يستمر |
| 3 | P2(R=2), P3(R=1) | P3 | P1 ينتهي (CT=3)، P3 يبدأ (RT=3-2=1) |
| 4 | P2(R=2), P3(R=0) | P2 | P3 ينتهي (CT=4)، P2 يبدأ (RT=4-1=3) |
| 5 | P2(R=1) | P2 | P2 يستمر |
| 6 | - | - | P2 ينتهي (CT=6) |

**النتيجة:**
```
SRTF Gantt: [1, 1, 1, 3, 2, 2]

P1: TAT=3-0=3, WT=3-3=0, RT=0
P2: TAT=6-1=5, WT=5-2=3, RT=4-1=3
P3: TAT=4-2=2, WT=2-1=1, RT=3-2=1

Avg WT = (0+3+1)/3 = 1.33 ms
Avg TAT = (3+5+2)/3 = 3.33 ms
Avg RT = (0+3+1)/3 = 1.33 ms
```

### محاكاة Priority:

| الوقت | العمليات المتاحة | الأولويات | يختار | ملاحظة |
|------|------------------|----------|------|--------|
| 0 | P1 | - | P1 | P1 يبدأ |
| 1 | P1(Pri=2), P2(Pri=1) | 2, 1 | P2 | P2 له أولوية أعلى، P1 ينقطع! |
| 2 | P1(Pri=2), P2(Pri=1), P3(Pri=3) | 2, 1, 3 | P2 | P2 يستمر |
| 3 | P1(Pri=2), P3(Pri=3) | 2, 3 | P1 | P2 ينتهي (CT=3) |
| 4 | P1(Pri=2), P3(Pri=3) | 2, 3 | P1 | P1 يستمر |
| 5 | P1(R=0), P3(Pri=3) | -, 3 | P3 | P1 ينتهي (CT=5)، P3 يبدأ |
| 6 | - | - | - | P3 ينتهي (CT=6) |

**النتيجة:**
```
Priority Gantt: [1, 2, 2, 1, 1, 3]

P1: TAT=5-0=5, WT=5-3=2, RT=0
P2: TAT=3-1=2, WT=2-2=0, RT=1-1=0
P3: TAT=6-2=4, WT=4-1=3, RT=5-2=3

Avg WT = (2+0+3)/3 = 1.67 ms
Avg TAT = (5+2+4)/3 = 3.67 ms
Avg RT = (0+0+3)/3 = 1.0 ms
```

### المقارنة:

| المقياس | SRTF | Priority | الأفضل |
|--------|------|----------|--------|
| Avg WT | 1.33 | 1.67 | **SRTF** ✓ |
| Avg TAT | 3.33 | 3.67 | **SRTF** ✓ |
| Avg RT | 1.33 | 1.0 | **Priority** ✓ |

---

## 📋 ملخص الشروط المطلوبة

### مدخلات صحيحة:
```
✓ PID: رقم موجب، لم يُستخدم من قبل
✓ Arrival: رقم ≥ 0
✓ Burst: رقم > 0
✓ Priority: رقم ≥ 1
```

### مدخلات خاطئة:
```
✗ أي حقل ليس رقماً
✗ Arrival < 0
✗ Burst ≤ 0
✗ Priority < 1
✗ PID مكرر
```

### عملية محاكاة صحيحة:
```
✓ العمليات تُشغّل فقط بعد وصولها
✓ العملية تستمر بالتشغيل حتى تنتهي
✓ الخوارزميات تختار العملية الصحيحة بناءً على القاعدة
✓ جميع المقاييس تُحسب صحيحاً
```

---

## 🎯 تدفق التطبيق الكامل

```
1. [التطبيق يبدأ]
   ↓
2. [App.java - start()]
   ↓
3. [InputScene يظهر]
   - اختر: إدخال يدوي أم سيناريو جاهز
   ↓
4. [المستخدم يدخل البيانات]
   ├─ Validator يتحقق من كل عملية
   ├─ ProcessForm تجمع البيانات
   ├─ ProcessTablePanel تعرضها
   ↓
5. [المستخدم ينقر Begin Simulation]
   ├─ App.deepCopy() ينسخ العمليات
   ├─ SJFScheduler.schedule() يشغّل SRTF
   ├─ PriorityScheduler.schedule() يشغّل Priority
   ├─ MetricsCalculator يحسب جميع القيم
   ↓
6. [ResultScene يظهر مع النتائج]
   ├─ MetricCards تعرض المقاييس
   ├─ BreakdownTables تعرض التفاصيل
   ├─ GanttCharts ترسم الجداول الزمنية
   ├─ ComparisonSummary تحلل المقارنة
   ├─ ConclusionPanel تعطي التوصيات
   ↓
7. [المستخدم ينقر Back]
   ↓
8. [العودة إلى InputScene]
```

---

## 📊 جدول الملفات والمسؤوليات

| الملف | المسؤولية | المدخلات | المخرجات |
|------|----------|---------|---------|
| **Process.java** | نموذج البيانات | (أرقام) | Process object |
| **BaseScheduler.java** | خوارزمية التخطيط العامة | Process list | Gantt log, مقاييس |
| **SJFScheduler.java** | اختيار بناءً على الوقت | currentTime, processes | Process object |
| **PriorityScheduler.java** | اختيار بناءً على الأولوية | currentTime, processes | Process object |
| **Validator.java** | التحقق من الصحة | String inputs | true/false أو error msg |
| **MetricsCalculator.java** | حساب المقاييس | Process list | قيم ومتوسطات |
| **App.java** | نقطة البداية | - | يشغّل التطبيق |
| **InputScene.java** | شاشة الإدخال | - | معالج callbacks |
| **ResultScene.java** | شاشة النتائج | Results data | شاشة معروضة |
| **UIComponents** | عناصر الواجهة | بيانات | عناصر رسومية |

---

**تم! هذه خريطة شاملة ومفصلة لكل المشروع ✅**
