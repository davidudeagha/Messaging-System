# Submission Checklist

* Code should compile as expected, i.e. with `java *java`,
  and run as expected without requiring any linking of
  libraries, starting servers, changing firewall rules.
* The submitted zip folder on Canvas should include the
  source files, i.e. the `.java` files. You do not need to
  submit the `.class` files (but there is no harm in doing
  so).
* The submission on Canvas should have the same source files
  as on your GitLab repository.
* Your repository should be accessible by all TAs. The list
  of TAs can be found
  [here](https://git.cs.bham.ac.uk/mhe/SWW-2017-2018/blob/master/Assignments/listOfTAs.md).
* Your submission must be able to run on a lab machine on
  your TA's account. For example, any libraries used in your
  solution should be available on the lab machines' version
  of Java (which you can find out with `java -version`).
  This is particularly relevant if you intend to use JavaFX,
  which is not available in openJDK.
* Your solution may support a GUI, for example, but it must
  support interaction via the terminal/command line. The
  terminal/command line interaction should be the default
  mode, and GUIs or other forms of interaction should
  require some explicit flag, which should be explained in
  SOLUTION.md.
* Features that are not documented in SOLUTION.md will not
  be marked.
* Ensure extra features (to obtain >=75%) do not break the
  existing solution.  For example, if you are implementing a
  password feature, but you may break existing functionality
  by not allowing existing users to login because the
  password feature is broken.
* If you submit by 5pm Tuesday 20/02/2018 you will receive
  5% extra marks, i.e. your mark will be multiplied by 1.05,
  but if you find bugs you can resubmit before Friday (but
  will not receive the additional 5%).
* For the extra features to qualify, you should have
  obtained a first class mark for the base features.

We would like to further emphasise adherence to the
compilation and execution specification. TAs will not have
time to figure out how to compile or run your solution. If
your solution currently requires some special compilation
procedure, additional libraries on the classpath, external
hardware, credentials to access a database, etc., please
modify your solution before submission so that there are no
impediments to marking.

