[33mcommit 07111888eb9a42448913cf1b543937ff73520018[m[33m ([m[1;36mHEAD -> [m[1;32mmeaningful_exceptions[m[33m, [m[1;31morigin/meaningful_exceptions[m[33m)[m
Merge: ba8b16e cc1a160
Author: Tomasz Pawlak <tomasz.pawlak@centre4.local>
Date:   Mon Nov 11 16:18:39 2019 +0000

    Merge branch 'EnterPointBuilderInterface_added' into meaningful_exceptions

[33mcommit ba8b16efc48f19d56bc024e9fc7b4bef8d648b66[m
Merge: ac14748 9e14cf4
Author: Tomasz Pawlak <tomasz.pawlak@centre4.local>
Date:   Mon Nov 11 16:06:31 2019 +0000

    Merge branch 'meaningful_exceptions' of https://src.thetestpeople.com/development-academy/java-parsons into meaningful_exceptions

[33mcommit ac1474834be627ea60e2365c909f07b11e287475[m
Author: Tomasz Pawlak <tomasz.pawlak@centre4.local>
Date:   Mon Nov 11 15:52:22 2019 +0000

    ExerciseBuilder added
    Instead of creating exercises by WholeClassExercise constructor,
     builder pattern was implemented and will be use to create a new exercises in ExerciseRepository.
     This approach makes whole process of initializing new exercises cleaner and easier.

[33mcommit cc1a1605bab6fb8cd9f997f105d58b7ae8c8a45f[m[33m ([m[1;31morigin/EnterPointBuilderInterface_added[m[33m, [m[1;32mEnterPointBuilderInterface_added[m[33m)[m
Author: Tomasz Pawlak <tomasz.pawlak@centre4.local>
Date:   Mon Nov 11 09:29:56 2019 +0000

    Exercises 4 & 5 fixed
    validate() method changed in ReturnTypeChecker class.

[33mcommit d43bf812c32a04b62aa9457a756e36d197234f4b[m
Author: Tomasz Pawlak <tomasz.pawlak@centre4.local>
Date:   Thu Nov 7 13:33:22 2019 +0000

    Rebuild EntryPointBuilder interface, ThreadSolutionRunner class changed.
    
    EntryPointBuilder added instead of EntryPoint.Additional interface LoadedEntryPoint was added.
    ThreadSolutionRunnerTest rebuilded, now we are using new version of implementation for EntryPoint.
    ThreadSolutionRunner class is not necessary anymore (?)

[33mcommit 9e14cf4804d8f7ce39abfab81c5ed4eb1da1727d[m
Author: Rebecca Foster <rebecca.foster@centre4.local>
Date:   Tue Nov 5 14:06:51 2019 +0000

    deleted tests for exercise 7

[33mcommit 7b3a8a945691856564a6592aa6c443117dd97c19[m
Author: Rebecca Foster <rebecca.foster@centre4.local>
Date:   Tue Nov 5 11:30:39 2019 +0000

    meaningful_exceptions

[33mcommit 03fefc1ef7c429bf1c3efa36fc46c9a1d4574b2e[m
Merge: 5acf3b0 478b40b
Author: Peter Russell <peter.russell@ten10.com>
Date:   Mon Nov 4 09:08:01 2019 +0000

    Merge branch 'ExercisePathImplementation'

[33mcommit 478b40b450ec9b91b9776f8b6cc8f80acaad594b[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Tue Aug 20 12:06:24 2019 +0100

    Fixed issue with setPort method throwing nullPointerException by adding try-catch for null pointer exception

[33mcommit 5acf3b030a1a68321f1b5a23e578d5dea427fe79[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Tue Aug 20 12:06:04 2019 +0100

    Testing getExerciseArraySize()

[33mcommit 635aa453d7d5cbbfd475467b99de8ad3462eeb1d[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 19 16:27:36 2019 +0100

    Added automated tests for Exercise Paths exercise

[33mcommit a3ff6d43f0db77270ea32241b44c53a784e25e3c[m
Merge: f9f418a ed195d8
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 19 16:19:32 2019 +0100

    Merge branch 'master' into BuildersAndCheckers14

[33mcommit f9f418ade8201536be68dfc44e325f4c92ac394a[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 19 16:12:48 2019 +0100

    Merge branch Master

[33mcommit 895d252c4e815220e92ef9a84f67f65c42790bc6[m
Merge: 21a4945 ed195d8
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 19 14:59:36 2019 +0100

    Merge branch 'master' of https://src.thetestpeople.com/development-academy/java-parsons into Joseph.Sayer

[33mcommit 21a49454e1f50bd9f71f6a1382593c9abc064151[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 19 13:15:40 2019 +0100

    Changed condition to switch between textbox and dropdowns into a more general case for potential other dropdown exercises as opposed to specifying one exercise (assuming all other dropdown exercises have 2 or more dropdowns required)

[33mcommit 97af9be5e7f093d97afcd653bfdedf8a4ee8422c[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 19 12:36:16 2019 +0100

    Extracted readEPE method from postAnswerEPE to make it variable for reading different numbers of dropdown menus

[33mcommit f6812ac85b5fae9b5ab0b10c59b1626fb1193f08[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 19 11:45:59 2019 +0100

    Extracted readEPE method from postAnswerEPE to make it variable for reading different numbers of dropdown menus

[33mcommit ed195d85ec31130da603802caa2214c4cae721f8[m
Merge: adf9375 2ec1217
Author: Peter Russell <peter.russell@ten10.com>
Date:   Mon Aug 19 10:59:51 2019 +0100

    Merge branch 'ExercisePathImplementation'

[33mcommit 2ec121727907bc946d3130bcf1e6ceeefdf68dc2[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Fri Aug 16 16:21:35 2019 +0100

    Easter egg boolean condition for EPE exercise modified to account for different numbers of dropdown inputs.

[33mcommit 11098ebbddb987ff0d202ff681380ae0aa6b36a5[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Fri Aug 16 15:22:01 2019 +0100

    Linked dropdown menu count to exercise information to allow potential other exercises to have a different number of dropdown menus if necessary.

[33mcommit d0ca374d532c773314eb768253b4e9b2afc31195[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Fri Aug 16 11:00:08 2019 +0100

    Moved dropdown list options to java enum source as opposed to javascript and sourced via Request Mapping/Response Body

[33mcommit f5dedf4faaed0925da51e95a9b9839ed5f8b078b[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 16:42:58 2019 +0100

    Fixed issue where extra dropdown menus were added on loading Exercise 7 multiple times.

[33mcommit 55cdf2c122bf15bb135ea6789db2a9e487ab17b4[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 16:09:01 2019 +0100

    Dropdown menus are built via loops in the js file, not the html file, and as such the number buildable can potentially be modified.

[33mcommit 6c228ab42cee0762ea1a92721a165fb7c592c708[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 15:15:03 2019 +0100

    Dropdown options now have ids

[33mcommit e20f9fcb78e206911881e399a314adc2cc92c554[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 15:06:48 2019 +0100

    CSS changes

[33mcommit 6af55969033732c760b8d3435eda39de41fc459d[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 14:04:37 2019 +0100

    (Line tabbing)

[33mcommit f42053336afee0d9585d303c368c58f72ac68817[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 13:35:31 2019 +0100

    CSS style formatting of dropdown input menus

[33mcommit 80a59844d729a6df1e73bc4451ad3c88912d9e4d[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 13:06:12 2019 +0100

    Removed unnecessary "}" option duplication from dropdown list

[33mcommit 943b626efdd4f4c4ae106aa945cda6486fd7b4e1[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 12:39:44 2019 +0100

    Pulled master changes

[33mcommit fbc924c7c8ed31c6e4df449e59e99cb7fd357e80[m
Merge: a240d02 adf9375
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 12:25:44 2019 +0100

    Merge branch 'master' of https://src.thetestpeople.com/development-academy/java-parsons into Joseph.Sayer

[33mcommit adf93756d43892d79f9788b0100d0db9de41cfcd[m
Merge: bdcdade e89d833
Author: Thomas.Crabtree <thomas.crabtree@ten10.com>
Date:   Thu Aug 15 11:56:54 2019 +0100

    merging to master (again)

[33mcommit bdcdade607d56a9b822dfe9198db546ad553f924[m
Merge: 3129884 fe8c00b
Author: Thomas.Crabtree <thomas.crabtree@ten10.com>
Date:   Thu Aug 15 11:48:50 2019 +0100

    Merging to master

[33mcommit a240d0282e94d8b559e13c46c2589f9ab41fd994[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 11:46:24 2019 +0100

    Reverted unintentional changes to acceptance-tests pom.xml file

[33mcommit 64cee564da125b847b1abffdebc35272b7760bec[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 11:17:55 2019 +0100

    Separate css and js files created for index stylesheet and javascript functions

[33mcommit ee019cd6e485b7bbcd8f11d840b83524abedf84f[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 10:59:52 2019 +0100

    Dropdown exercise operational in basic form

[33mcommit 3a53096c16f8e51e3b8745786b8be426ac5357b9[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 15 10:55:54 2019 +0100

    Added new exercise to implement new exercise-type (implementation TBC)

[33mcommit ea3e36e5a2f38e95cb3b44757f80bef3eb6a53e0[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Thu Aug 15 10:19:10 2019 +0100

    WIP - unify CompleteCodeExercise with WholeClassExercise

[33mcommit e89d83360814ae8d105427192a2d8a6ed5132cce[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Wed Aug 14 16:53:24 2019 +0100

    remove get method output

[33mcommit 8b91d534c0661d429cbe36643aaaa7c721eeca7f[m
Merge: f4da3f4 fe8c00b
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Wed Aug 14 15:42:21 2019 +0100

    Merge branch 'remove_get_method_output' of https://src.thetestpeople.com/development-academy/java-parsons into remove_get_method_output

[33mcommit f4da3f42eb07cb272136e57a84dc2aa45ae334e2[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Tue Aug 13 16:10:16 2019 +0100

    Remove get method output from the solution runner interface

[33mcommit 4ed35a4a1d582fb6154d9a202b88107ec1ef17ea[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Mon Aug 12 16:24:00 2019 +0100

    Removed Usage of GetMethodOuput() From within the solution runner interface. Implemented an
    interface within the solution runner called run result. Implemented a compiler error report for
    return type solution, that will report a compiler error if there isnt a return value given.

[33mcommit 9c5116c3c46c07a22a4a3789bcdb8e645e88c635[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Fri Aug 9 16:07:35 2019 +0100

    Removed Usage of GetMethodOuput() From within the solution runner interface.

[33mcommit 245a9092548d8337097f07930229695fd32c827a[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Thu Aug 8 16:53:34 2019 +0100

    Removed usage of the method getMethodOutput that is within the SolutionRunner interface, from the ReturnTypeExcerciseSolution.java class

[33mcommit 51b8326a77c1d38ad23604b337be329e494b0a7f[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Thu Aug 8 15:26:23 2019 +0100

    Removed usage of the method getMethodOutput that is within the SolutionRunner interface, from the ReturnTypeExcerciseSolution.java class

[33mcommit 488265882a857bd8f2edbab6c95d6c2e52f152ae[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Wed Aug 14 10:46:43 2019 +0100

    Added new exercise to implement new exercise-type (implementation TBC)

[33mcommit 3129884746fcd59e8d314d03697fdedd3d1acfda[m
Merge: d378ab1 27026fb
Author: Thomas.Crabtree <thomas.crabtree@ten10.com>
Date:   Wed Aug 14 10:23:09 2019 +0100

    Merge branch 'Joseph.Sayer'

[33mcommit fe8c00b2725243356847717b99dacd1f0e276c9f[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Tue Aug 13 16:10:16 2019 +0100

    Remove get method output from the solution runner interface

[33mcommit 27026fbe8a2b89a25f8f3c2e41966c9e33c85f85[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Tue Aug 13 11:15:52 2019 +0100

    Implemented Mo's changes

[33mcommit e4e56f398f922fec5c282aab4c9aff2cd3073392[m
Merge: f4f72d7 d378ab1
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Tue Aug 13 10:48:09 2019 +0100

    Merge branch 'master' of https://src.thetestpeople.com/development-academy/java-parsons
    
    # Conflicts:
    #       acceptance-tests/src/test/java/com/ten10/training/javaparsons/acceptancetests/ExersisePageObjects/CompleteTheCodeExercise.java
    #       acceptance-tests/src/test/java/com/ten10/training/javaparsons/acceptancetests/ExersiseTests/CompleteTheCodeHelloWorldAT.java

[33mcommit d378ab1393386328cbacb37abb8a8d10c3238536[m
Merge: 76d675f 5c35159
Author: Thomas.Crabtree <thomas.crabtree@ten10.com>
Date:   Tue Aug 13 10:11:36 2019 +0100

    Merge branch 'WIP-Tests'

[33mcommit 79e72f387b6b708572f35fc56ed4b2926590d79d[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Mon Aug 12 16:24:00 2019 +0100

    Removed Usage of GetMethodOuput() From within the solution runner interface. Implemented an
    interface within the solution runner called run result. Implemented a compiler error report for
    return type solution, that will report a compiler error if there isnt a return value given.

[33mcommit 5c35159030e47b8a15334d08699036a1351703ef[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 12 14:57:49 2019 +0100

    Tag Acceptance Tests for Jenkins

[33mcommit d748ab5d133017cf2429963639fb7a042ca6ee1d[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 12 14:03:48 2019 +0100

    Refactor acceptance tests
    
    Removing multiple object pages for each acceptance test and refactoring to use a common object page.

[33mcommit f4f72d7c9f53dc16dd9ce71b47a3b964205ba33c[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 12 13:50:50 2019 +0100

    Removed 2 out of 3 stability warnings

[33mcommit 77bec12a6122e55c80afe9fd4873753544b5d84c[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 12 11:40:41 2019 +0100

    Refactor acceptance tests

[33mcommit ac15827efa67fbe7258e3ca8ca0683b28e507722[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Mon Aug 12 11:34:55 2019 +0100

    Added timeout error message as listable runner error and fixed timeout error test.

[33mcommit 038074112bfa4c4424094a81f52bcea874cd4ca1[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 12 10:04:26 2019 +0100

    Refactor acceptance tests
    
    Removing multiple object pages for each acceptance test and refactoring to use a common object page.(tidy up imports)

[33mcommit cfbce8eee515774f50f88121526e5cfb24baab77[m
Author: mohammed.mumtaz <mohammed.mumtaz@ten10.com>
Date:   Mon Aug 12 09:24:22 2019 +0100

    Refactor acceptance tests
    
    Removing multiple object pages for each acceptance test and refactoring to use a common object page.

[33mcommit d2dcdcfe13404bff8a3a215ba4598f9efc59cc87[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Fri Aug 9 16:07:35 2019 +0100

    Removed Usage of GetMethodOuput() From within the solution runner interface.

[33mcommit aac853c52f0d7f617e161421552db9e7eb09e800[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Fri Aug 9 11:38:04 2019 +0100

    Added automated test for timeout message box (needs modifying once timeout message has been added to display correct message as opposed to current one)

[33mcommit 2fd1f226fe10ae91c81381eb3e590d5591c5f377[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Thu Aug 8 16:53:34 2019 +0100

    Removed usage of the method getMethodOutput that is within the SolutionRunner interface, from the ReturnTypeExcerciseSolution.java class

[33mcommit ae71afc03c594fa2c046a8b0648aec700c51ad39[m
Author: jamie.fidoe <jamie.fidoe@ten10.com>
Date:   Thu Aug 8 15:26:23 2019 +0100

    Removed usage of the method getMethodOutput that is within the SolutionRunner interface, from the ReturnTypeExcerciseSolution.java class

[33mcommit 8c97772091afc9a8f2aed40a3efca3ab43bbbcca[m
Author: joseph.sayer <joseph.sayer@ten10.com>
Date:   Thu Aug 8 15:06:17 2019 +0100

    Changed name of postRequestExercise() to markUserInput()

[33mcommit ec114f3daac7cf93c119d1ed2ac65bf4c1bb2c0b[m
Author: Peter Russell <peter.russell@ten10.com>
Date:   Thu Aug 8 15:01:38 2019 +0100

    WIP migrate tests to SeleniumExtension and start rewrite
    
    * Switch tests to SeleniumExtension
    * Fix a bug in the webpage
    * Add a synchronization mechanism
    * Move HelloWorld and GoodbyeWorld tests to share a single page object.

[33mcommit d206d7b842c73b41449862e2cfd322a84e684a3f[m
Author: Peter Russell <peter