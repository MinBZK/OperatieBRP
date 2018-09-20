Overview:
=========
  A simple quickstart demonstrating execution of BeanShell, Jython and JRuby
  scripts using the ScriptingAction, and Groovy using the GroovyActionProcessor.
  When BSF (the Apache Bean Scripting Framework) is upgraded from 2.3.0 to
  2.4.0, the ScriptingAction will then automatically support JavaScript (via
  Mozilla Rhino) as well as Groovy directly.  This upgrade will also require BSF
  (BeanShell) to be upgraded from 1.3.0 to 2.0b4.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
