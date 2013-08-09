module ScoreHelper

def renderLink (*names)
  	javascript = "javascript"
  	link = "link"
  	script = "script"
  	part1 = "<script type=\"text/javascript\">\n"
  	part2 = "\tvar head = document.getElementsByTagName(\"head\")[0];"
  	part3 = ""
  	part4 = "</script>"
  	names.each {|name, i|
	  	type = name.partition("\.")[2].downcase
	  	type = (type.eql? "js") ? javascript : type
	  	isCss = (type.eql? "css")
	  	isJavaScript = (type.eql? javascript)
	  	element = isJavaScript ? script : link
  		part3 += "\tvar #{element}#{i} = document.createElement(\"#{element}\");\n"
  		part3 += "\t#{element}#{i}.setAttribute(\"rel\",\"stylesheet\");\n" if isCss
  		part3 += "\t#{element}#{i}.setAttribute(\"type\",\"text/" + type + "\");\n"
  		part3 += "\t#{element}#{i}.setAttribute(\"href\",\"/static/score/" + name +"\");\n"
  		part3 += "\thead.appendChild(#{element}#{i});\n"		
  	}
  	return part1 + part2 + part3 + part4
  end
  
  def renderScoreNavBar
  	navOptions = Array[["Users", "userlists","user"], ["Teams", "teamlist","team"], ["Projects", "projectlist","project"]]
  	openDiv = "<div id=\"score-nav\">\n"
  	divContents = ""
  	closeDiv = "\n</div>"
  	lastIndex = navOptions.length - 1
  	i = 0
  	navOptions.each { |option|
  		isCurrent = request.url.include? option[2]
  		divContents += "<a href=\"/#{option[1]}\"#{isCurrent ? "class=\"current\"><b>" : ">"} "
  		divContents += "#{option[0]} #{isCurrent ? "</b>" : ""}</a>"
  		if (i < lastIndex)
  			divContents += "<span> | </span>"
  		end
  	}
  	return openDiv + divContents + closeDiv
  end
end