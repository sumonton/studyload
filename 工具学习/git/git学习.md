# git操作

* 现在远程仓库创建项目，为了避免错误，先不要创建README，license，或者gitignore文件。

* 打开本地工程的Terminal。

## 1、git init

* 初始化本地仓库

## 2、git add .

* 将文件其加入暂存区（Stage 或者 Index）中。暂存区是提交之前的一个临时区域。
* 如果只是用 Git 仓库的工作树创建了文件，那么该文件并不会被记入 Git 仓库的版本管理对象当中。因此我们用 git status命令查看README.md 文件时，它会显示在 Untracked files 里。
* 要想让文件成为 Git 仓库的管理对象，就需要用 git add命令将其加入暂存区（Stage 或者 Index）中。

## 3、git commit -m "first commit"

* git commit命令可以将当前暂存区中的文件实际保存到仓库的历史记录中。通过这些记录，我们就可以在工作树中复原文件
* 记述详细提交信息
  * 刚才我们只简洁地记述了一行提交信息，如果想要记述得更加详细，请不加 - m，直接执行 g i t c o m m i t命令。执行后编辑器就会启动，并显示如下结果。
  * 在编辑器中记述提交信息的格式如下。
    *  第一行：用一行文字简述提交的更改内容
    * 第二行：空行
    * 第三行以后：记述更改的原因和详细内容

## 4、git log

* 查看提交日志

### 4.1 `git log --pretty=short`

* 只显示提交信息的第一行

### 4.2`git log hello-world.html`

* 只显示指定目录、文件的日志

### 4.3 `git log -p hello-world.html`

* 可以只查看置顶文件的提交日志以及提交前后的差别

## 5、git diff

* 可以查看工作树、暂存区，最新提交的差别

### 5.1 `git diff head`

* 查看本次提交与上次提交之间有什么差别，等确认完毕后再进行提交。这里的 HEAD 是指向当前分支中最新一次提交的指针。

## 6、git push

* push到远程仓库

## 7、到远程仓库的页面，复制工程地址

```
https每次fetch和push需要输账号密码
ssh不用，但需要在配置好sshkey
```

## 8、添加远程仓库到本地仓库

```
git remote add origin 远程地址
git remote -v 
#verfies the new remote URL
```

## 9、git status

* 工作树和仓库在被操作的过程中，状态会不断发生变化。在 Git 操作过程中时常用 git status命令查看当前状态，可谓基本中的基本



# 分支的操作

* 在进行多个并行作业时，会用到分支。在这类并行开发的过程中，往往都需要同时存在多个最新的代码状态。从 master 分支创建 feature-A 分支和 fix-B 分支后，每个分支中都拥有自己的最新代码。master 分支是 Git 默认创建的分支，因此基本上所有开发都是以这个分支为中心进行的。

  ![image-20230116174000897](https://raw.githubusercontent.com/sumonton/img/master/typora/202301161740976.png)

* 不同分支中，可以同时进行完全不同的作业。等该分支的作业完成之后再与 master 分支合并。比如 feature-A 分支的作业结束后与 master合并。

  ![image-20230116174407914](https://raw.githubusercontent.com/sumonton/img/master/typora/202301161744997.png)

* 通过灵活运用分支，可以让多人同时高效地进行并行开发。在这里，我们将带大家学习与分支相关的 Git 操作。

## 1、`git branch`查看分支

* 可以将分支列表显示，同时可以确认当前所在分支。''**\***''表示当前所在分支

  ```shell
  smc@linjianguodeMBP gitstudy % git branch
  * main
  ```

## 2、`git checkout -b`创建、切换分支

* 在master分支基础上创建分支

### 2.1 `git checkout -b feature-A`创建名为feature-A的分支

* 创建分支，并切换的到该分支

  ```shell
  smc@linjianguodeMBP gitstudy % git checkout -b feature-A
  切换到一个新分支 'feature-A'
  smc@linjianguodeMBP gitstudy % git branch
  * feature-A
    main
  ```

* 执行以下也能达到同样效果

  ```sh
  #创建feature-B分支
  smc@linjianguodeMBP gitstudy % git branch feature-B
  smc@linjianguodeMBP gitstudy % git branch
  * feature-A
    feature-B
    main
  #切换到feature-B分支
  smc@linjianguodeMBP gitstudy % git checkout feature-B
  切换到分支 'feature-B'
  smc@linjianguodeMBP gitstudy % git branch
    feature-A
  * feature-B
    main
  ```

### 2.2 `git checkout -`切换到上一个分支

### 2.3 特性分支

* 之前我们创建了 feature-A 分支，这一分支主要实现 feature-A，除feature-A 的实现之外不进行任何作业。即便在开发过程中发现了 BUG，也需要再创建新的分支，在新分支中进行修正。
* 基于特定主题的作业在特性分支中进行，主题完成后再与 master 分支合并。只要保持这样一个开发流程，就能保证 master 分支可以随时供人查看。这样一来，其他开发者也可以放心大胆地从 master 分支创建新的特性分支。

### 2.4 主干分支

* 主干分支是刚才我们讲解的特性分支的原点，同时也是合并的终点。通常人们会用 master 分支作为主干分支。主干分支中并没有开发到一半的代码，可以随时供他人查看。
* 有时我们需要让这个主干分支总是配置在正式环境中，有时又需要用标签 Tag 等创建版本信息，同时管理多个版本发布。拥有多个版本发布时，主干分支也有多个。

### 2.5 `git merge`合并分支

* 为了在历史记录中明确记录下本次分支合并，我们需要创建合并提交。因此，在合并时加上 --no-ff参数

```sh
smc@linjianguodeMBP gitstudy % git checkout main
切换到分支 'main'
您的分支与上游分支 'origin/main' 一致。
smc@linjianguodeMBP gitstudy % git merge --no-ff feature-A
Merge made by the 'ort' strategy.
 README.md | 1 +
 1 file changed, 1 insertion(+)

```

### 2.6 `git log --graph`以图表形式查看分支

* 能很清楚地看到特性分支（feature-A）提交的内容已被合并。除此以外，特性分支的创建以及合并也都清楚明了。

# 更改提交操作

## 1、`git reset`回溯历史版本

* Git 的另一特征便是可以灵活操作历史版本。借助分散仓库的优势，可以在不影响其他仓库的前提下对历史版本进行操作。

### 1.1 回溯历史，创建fix-B分支

* 回溯到创建featrue—A分支前

  * 要让仓库的 HEAD、暂存区、当前工作树回溯到指定状态，需要用到 git reset --hard命令。只要提供目标时间点的哈希值 A，就可以完全恢复至该时间点的状态。事不宜迟，让我们执行下面的命令。

    ```shell
    smc@linjianguodeMBP gitstudy % git log --graph
    *   commit 3b33d176ed2ff8dbe7662b7527b046a0719b8a6a (HEAD -> main, origin/main, origin/HEAD)
    |\  Merge: 4b3fa7b 6143af1
    | | Author: smc <975952433@qq.com>
    | | Date:   Mon Jan 16 18:05:54 2023 +0800
    | | 
    | |     Merge branch 'feature-A'
    | |     分支合并测试
    | | 
    | * commit 6143af1a87f392177201024b7473048e2e9cfb8f (feature-A)
    |/  Author: smc <975952433@qq.com>
    |   Date:   Mon Jan 16 17:55:54 2023 +0800
    |   
    |       git add branchA test
    | 
    * commit 4b3fa7b66b0820c75265a9d8ff1ab7574d16e423 (feature-B)
    | Author: smc <975952433@qq.com>
    | Date:   Mon Jan 16 17:32:40 2023 +0800
    | 
    |     git diff test
    | 
    * commit 1ca775b69fcbc927b969cadd4baff1618fa89d7a
    | Author: smc <975952433@qq.com>
    smc@linjianguodeMBP gitstudy % git reset --hard 4b3fa7b66b0820c75265a9d8ff1ab7574d16e423
    HEAD 现在位于 4b3fa7b git diff test
    ```

  * 我们已经成功回溯到特性分支（feature-A）创建之前的状态。由于所有文件都回溯到了指定哈希值对应的时间点上，README.md 文件的内容也恢复到了当时的状态。

* 创建fix-B特性分支

  ```shell
  smc@linjianguodeMBP gitstudy % git check -b fix-B
  git：'check' 不是一个 git 命令。参见 'git --help'。
  
  最相似的命令是
  	checkout
  smc@linjianguodeMBP gitstudy % git checkout -b fix-B
  切换到一个新分支 'fix-B'
  smc@linjianguodeMBP gitstudy % git add README.md 
  smc@linjianguodeMBP gitstudy % git commit -m "git reset fix-B测试"
  [fix-B 1c82fd9] git reset fix-B测试
   1 file changed, 1 insertion(+)
  smc@linjianguodeMBP gitstudy % 
  ```

* 推进到feature分支合并后的状态

  * git log命令只能查看以当前状态为终点的历史日志。所以这里要使用 git reflog命令，查看当前仓库的操作日志。在日志中找出回溯历史之前的哈希值，通过 git reset --hard命令恢复到回溯历史前的状态。

    ```shell
    smc@linjianguodeMBP gitstudy % git reflog
    1c82fd9 (HEAD -> fix-B) HEAD@{0}: commit: git reset fix-B测试
    4b3fa7b (main, feature-B) HEAD@{1}: checkout: moving from main to fix-B
    4b3fa7b (main, feature-B) HEAD@{2}: reset: moving to 4b3fa7b66b0820c75265a9d8ff1ab7574d16e423
    3b33d17 (origin/main, origin/HEAD) HEAD@{3}: merge feature-A: Merge made by the 'ort' strategy.
    4b3fa7b (main, feature-B) HEAD@{4}: checkout: moving from feature-A to main
    6143af1 (feature-A) HEAD@{5}: checkout: moving from main to feature-A
    4b3fa7b (main, feature-B) HEAD@{6}: checkout: moving from feature-A to main
    6143af1 (feature-A) HEAD@{7}: checkout: moving from main to feature-A
    4b3fa7b (main, feature-B) HEAD@{8}: checkout: moving from feature-A to main
    6143af1 (feature-A) HEAD@{9}: commit: git add branchA test
    4b3fa7b (main, feature-B) HEAD@{10}: checkout: moving from feature-B to feature-A
    4b3fa7b (main, feature-B) HEAD@{11}: checkout: moving from feature-A to feature-B
    4b3fa7b (main, feature-B) HEAD@{12}: checkout: moving from main to feature-A
    4b3fa7b (main, feature-B) HEAD@{13}: commit: git diff test
    1ca775b HEAD@{14}: commit: second html push
    b8abbdb HEAD@{15}: commit: 提交test
    c9a1264 HEAD@{16}: commit: sed
    9fe74d1 HEAD@{17}: clone: from github.com:sumonton/gitstudy.git
    smc@linjianguodeMBP gitstudy % git reset --hard 3b33d17
    HEAD 现在位于 3b33d17 Merge branch 'feature-A' 分支合并测试
    ```

  * 在日志中，我们可以看到 commit、checkout、reset、merge 等 Git 命令的执行记录。只要不进行 Git 的 **GC（Garbage Collection，垃圾回收）**，就可以通过日志随意调取近期的历史状态，就像给时间机器指定一个时间点，在过去未来中自由穿梭一般。即便开发者错误执行了 Git 操作，基本也都可以利用 git reflog命令恢复到原先的状态。

  * 恢复历史后状态

    ![image-20230116182616632](https://raw.githubusercontent.com/sumonton/img/master/typora/202301161826720.png)

* 消除冲突

  * 现在只要合并 fix-B 分支，就可以得到我们想要的状态。让我们赶快进行合并操作。

    ```shell
    smc@linjianguodeMBP gitstudy % git merge --no-ff fix-B
    自动合并 README.md
    冲突（内容）：合并冲突于 README.md
    自动合并失败，修正冲突然后提交修正的结果。
    ```

    * 这时，系统告诉我们 README.md 文件发生了冲突（Conflict）。系统在合并 README.md 文件时，feature-A 分支更改的部分与本次想要合并的 fix-B 分支更改的部分发生了冲突。

* 查看冲突部分并将其解决,解决完后直接git add和commit

### 1.2 `git commit --amend`修改提交信息

### 1.3 `git rebase -i HEAD~2`压缩历史

* 在合并特性分支之前，如果发现已提交的内容中有些许拼写错误等，不妨提交一个修改，然后将这个修改包含到前一个提交之中，压缩成一个历史记录。这是个会经常用到的技巧，让我们来实际操作体会一下

  * 第一次提交

    ```shell
    smc@linjianguodeMBP gitstudy % git commit -am "git rebase feature-C测试"
    [feature-C 77e6abe] git rebase feature-C测试
     1 file changed, 2 insertions(+), 1 deletion(-)
    ```

  * 发现拼写错误，修改后重新提交

    ```shell
    smc@linjianguodeMBP gitstudy % git diff
    diff --git a/README.md b/README.md
    index 17f50eb..b30241f 100644
    --- a/README.md
    +++ b/README.md
    @@ -3,4 +3,4 @@ github学习知识库
     git branch 测试1
     合并冲突git branch feature-B 
     合并冲突fix-B
    -git rebase feature-C
    \ No newline at end of file
    +git rebase feature-C 测试
    \ No newline at end of file
    ```

  * 将两个提交压缩成一个提交

    ```shell
    smc@linjianguodeMBP gitstudy % git commit -am "git rebase 修改"
    [feature-C c368b55] git rebase 修改
     1 file changed, 1 insertion(+), 1 deletion(-)
    ```

    * 用上述方式执行 git rebase命令，可以选定当前分支中包含HEAD（最新提交）在内的两个最新历史记录为对象，并在编辑器中打开

      ```shell
      pick 7a34294 Add feature-C
      pick 6fba227 Fix typo
      ```

    * 我们将 6fba227 的 Fix typo 的历史记录压缩到 7a34294 的 Add feature-C里。按照下图所示，将 6fba227 左侧的 pick 部分删除，改写为 fixup。

      ```shell
      pick 7a34294 Add feature-C
      fixup 6fba227 Fix typo
      ```

    * 保存编辑器里的内容，关闭编辑器。

      ```shell
      [feature-C c368b55] git rebase 修改
       1 file changed, 1 insertion(+), 1 deletion(-)
      ```

  * 合并feature-C

    ```shell
    smc@linjianguodeMBP gitstudy % git merge --no-ff feature-C
    Merge made by the 'ort' strategy.
     README.md | 3 ++-
     1 file changed, 2 insertions(+), 1 deletion(-)
    ```

## 2、推送至远程仓库

### 2.1 `git push -u origin master`推送至master分支

* 像这样执行 git push命令，当前分支的内容就会被推送给远程仓库origin 的 master 分支。-u参数可以在推送的同时，将 origin 仓库的 master 分支设置为本地仓库当前分支的 upstream（上游）。添加了这个参数，将来运行 git pull命令从远程仓库获取内容时，本地仓库的这个分支就可以直接从 origin 的 master 分支获取内容，省去了另外添加参数的麻烦。

```shell
smc@linjianguodeMBP gitstudy % git push -u origin main   
枚举对象中: 16, 完成.
对象计数中: 100% (16/16), 完成.
使用 4 个线程进行压缩
压缩对象中: 100% (14/14), 完成.
写入对象中: 100% (14/14), 1.38 KiB | 1.38 MiB/s, 完成.
总共 14（差异 9），复用 0（差异 0），包复用 0
remote: Resolving deltas: 100% (9/9), completed with 1 local object.
To github.com:sumonton/gitstudy.git
   78d5164..4cacab6  main -> main
分支 'main' 设置为跟踪 'origin/main'
```



### 2.2 ` git push -u origin feature-D`推送至master以外的分支

* 我们在本地仓库中创建了 feature-D 分支，现在将它 push 给远程仓库并保持分支名称不变。在远程仓库的 GitHub 页面就可以查看到 feature-D 分支了。

```shell
smc@linjianguodeMBP gitstudy % git push -u origin feature-A
总共 0（差异 0），复用 0（差异 0），包复用 0
remote: 
remote: Create a pull request for 'feature-A' on GitHub by visiting:
remote:      https://github.com/sumonton/gitstudy/pull/new/feature-A
remote: 
To github.com:sumonton/gitstudy.git
 * [new branch]      feature-A -> feature-A
分支 'feature-A' 设置为跟踪 'origin/feature-A'。
smc@linjianguodeMBP gitstudy % 
```

## 3、从远程仓库获取

### 3.1 `git clone`获取远程仓库

* 执行 git clone命令后我们会默认处于 master 分支下，同时系统会自动将 origin 设置成该远程仓库的标识符。也就是说，当前本地仓库的 master 分支与 GitHub 端远程仓库（origin）的 master 分支在内容上是完全相同的

* 我们用 git branch -a命令查看当前分支的相关信息。添加 -a参数可以同时显示本地仓库和远程仓库的分支信息

  ```shell
  smc@linjianguodeMBP gitstudy % git branch -a
  * main
    remotes/origin/HEAD -> origin/main
    remotes/origin/feature-A
    remotes/origin/feature-B
    remotes/origin/main
  
  ```

### 3.2 `git checkout -b feature-D origin/feature-D`将远程的feature-D分支获取到本地仓库

* \- b 参数的后面是本地仓库中新建分支的名称。为了便于理解，我们仍将其命名为 feature-D，让它与远程仓库的对应分支保持同名。新建分支名称后面是获取来源的分支名称。例子中指定了 origin/feature-D，就是说以名为 origin 的仓库（这里指 GitHub 端的仓库）的 feature-D 分支为来源，在本地仓库中创建 feature-D 分支.

  ```shell
  smc@linjianguodeMBP gitstudy % git checkout -b feature-A origin/feature-A
  分支 'feature-A' 设置为跟踪 'origin/feature-A'。
  切换到一个新分支 'feature-A'
  
  ```

* 向feature-A分支提交修改

  ```shell
  smc@linjianguodeMBP gitstudy % git commit -am "feature-A分支提交"  
  [feature-A a3b5ba3] feature-A分支提交
   1 file changed, 1 insertion(+)
  smc@linjianguodeMBP gitstudy % git push
  枚举对象中: 5, 完成.
  对象计数中: 100% (5/5), 完成.
  使用 4 个线程进行压缩
  压缩对象中: 100% (3/3), 完成.
  写入对象中: 100% (3/3), 350 字节 | 350.00 KiB/s, 完成.
  总共 3（差异 1），复用 0（差异 0），包复用 0
  remote: Resolving deltas: 100% (1/1), completed with 1 local object.
  To github.com:sumonton/gitstudy.git
     6143af1..a3b5ba3  feature-A -> feature-A
  ```

* 拉去分支的修改

  ```shell
  smc@linjianguodeMBP gitstudy % git pull origin feature-A
  remote: Enumerating objects: 5, done.
  remote: Counting objects: 100% (5/5), done.
  remote: Compressing objects: 100% (2/2), done.
  remote: Total 3 (delta 1), reused 3 (delta 1), pack-reused 0
  展开对象中: 100% (3/3), 330 字节 | 110.00 KiB/s, 完成.
  来自 github.com:sumonton/gitstudy
   * branch            feature-A  -> FETCH_HEAD
     6143af1..a3b5ba3  feature-A  -> origin/feature-A
  提示：您有偏离的分支，需要指定如何调和它们。您可以在执行下一次
  提示：pull 操作之前执行下面一条命令来抑制本消息：
  提示：
  提示：  git config pull.rebase false  # 合并
  提示：  git config pull.rebase true   # 变基
  提示：  git config pull.ff only       # 仅快进
  提示：
  提示：您可以将 "git config" 替换为 "git config --global" 以便为所有仓库设置
  提示：缺省的配置项。您也可以在每次执行 pull 命令时添加 --rebase、--no-rebase，
  提示：或者 --ff-only 参数覆盖缺省设置。
  
  ```

# 快捷键和技巧

## 1、`shift+/`打开快捷键栏

## 2、查看分支的差别

* 查看分支间的差别比如我们想查看 4-0-stable 分支与 3-2-stable 分支之间的差别，可以像下面这样将分支名加到 URL 里。

  `https://github.com/rails/rails/compare/4-0-stable...3-2-stable`

* 查看几天的差别：假如我们想查看 master 分支在最近 7 天内的差别，可以像下面这样这样将时间加入 URL。

  `https://github.com/rails/rails/compare/master@{7.day.ago}...master`

* 查看指定日期的区别：

* 假设我们想查看 master 分支 2013 年 1 月 1 日与现在的区别，可以将日期加入 URL。

  `https://github.com/rails/rails/compare/master@{2013-01-01}...master`

* 获取diff格式和patch格式的文件

  * 对长期投身于软件开发的人来说，有时可能会希望以 diff 格式文件和 patch 格式文件的形式来处理 Pull Request。举个例子，假设 Pull Request 的 URL 如下所示。

    `https://github.com/用户名/仓库名/pull/28`

* 如果想获取 diff 格式的文件，只要像下面这样在 URL 末尾添加 .diff 即可。

  `https://github.com/用户名/仓库名/pull/28.diff`

* 同 理， 想 要 patch 格 式 的 文 件， 只 需 要 在 URL 末 尾 添加 .patch 即可。

  `https://github.com/用户名/仓库名/pull/28.patch`

# Pull Request的使用

## 1、pull request前的准备

* 概念图

  ![image-20230117165111019](https://raw.githubusercontent.com/sumonton/img/master/typora/202301171651171.png)

* 查看要修正的源代码

* Fork

* clone

* branch

  * 养成创建特性分支后再修改代码的好习惯。在 GitHub 上发送 Pull Request 时，一般都是发送特性分支。这样一来，Pull Request 就拥有了更明确的特性（主题）。让对方了解自己修改代码的意图，有助于提高代码审查的效率。
  * 确认分支 `git branch -a`
  * 创建特性分支` git checkout -b smc-work gh-pages `，并确认切换到该分支下
  * 添加代码，查看差异

  * add和commit

  * 创建远程分支

    ```shell
    smc@linjianguodeMBP smc-first-pr % git push origin smc-work
    枚举对象中: 5, 完成.
    对象计数中: 100% (5/5), 完成.
    使用 4 个线程进行压缩
    压缩对象中: 100% (3/3), 完成.
    写入对象中: 100% (3/3), 366 字节 | 366.00 KiB/s, 完成.
    总共 3（差异 2），复用 0（差异 0），包复用 0
    remote: Resolving deltas: 100% (2/2), completed with 2 local objects.
    remote: 
    remote: Create a pull request for 'smc-work' on GitHub by visiting:
    remote:      https://github.com/sumonton/smc-first-pr/pull/new/smc-work
    remote: 
    To github.com:sumonton/smc-first-pr.git
     * [new branch]      smc-work -> smc-work
    
    
    smc@linjianguodeMBP smc-first-pr % git branch -a
      gh-pages
    * smc-work
      remotes/origin/HEAD -> origin/gh-pages
      remotes/origin/gh-pages
      remotes/origin/smc-work
    ```

* 到**githup**发送**Pull Request**

# 项目管理工具（参考）

* Redmine

# Gist

* GistA 是一款简单的 Web 应用程序，常被开发者们用来共享示例代码和错误信息。开发者在线交流时难免会涉及软件日志的内容，但直接发送日志会占据很大的篇幅，给交流带来不便。这种情况下，笔者习惯把日志粘贴到 Gist，然后将 URL 发送给对方

# Git 小技巧

## 1、搜索技巧

### 1.1 按`S`快速锁定搜索栏

### 1.2 高级搜索

* `springboot vue stars:>1000 pushed:>2023-01-01 language:Java`
* 直接进入高级搜索页面

## 2、文件搜索技巧

### 2.1 按`t`文件搜索

### 2.2 按`L`快速跳到某一行

### 2.3 点击行号生成永久链接

### 2.4 按`b`键查看该文件改动记录

### 2.5 `commond+k`控制面板

## 3、阅读代码技巧

### 3.1 按`.`进入网页版vscode阅读代码

## 4、网页执行项目

### 4.1 在地址前面加上`gitpod.io/#/`前缀

* `https://gitpod.io/#/github.com/Z4nzu/hackingtool`

## 5、项目推送

* 在全球资源页面设置，让githup定期推送感兴趣的项目
* 推送设置地址：`https://github.com/explore/email`



