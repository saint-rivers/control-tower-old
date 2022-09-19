package com.saintrivers.controltower.exception

import org.springframework.dao.DuplicateKeyException

class MemberAlreadyAddedException : DuplicateKeyException("member already exists in group")