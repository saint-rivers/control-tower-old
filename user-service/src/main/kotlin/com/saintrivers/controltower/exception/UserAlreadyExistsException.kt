package com.saintrivers.controltower.exception

import org.springframework.dao.DuplicateKeyException

class UserAlreadyExistsException : DuplicateKeyException("user with this email already exists")