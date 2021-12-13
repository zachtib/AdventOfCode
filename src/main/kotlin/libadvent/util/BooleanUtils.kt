package libadvent.util


fun <T> Boolean.map(ifTrue: T, ifFalse: T): T = if (this) ifTrue else ifFalse