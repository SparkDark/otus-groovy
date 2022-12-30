import com.google.common.base.Joiner


def maps = ["India": "Hockey", "England": "Cricket"]


def joiner(maps){
 def delimiter = '#'
 def separator = ':'
 return Joiner.on(delimiter).withKeyValueSeparator(separator).join(maps);
}


println(joiner(maps))