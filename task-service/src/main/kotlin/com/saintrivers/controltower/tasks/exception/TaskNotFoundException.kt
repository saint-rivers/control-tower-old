package com.saintrivers.controltower.tasks.exception

import org.webjars.NotFoundException

class TaskNotFoundException: NotFoundException("cannot find task of the given id")

