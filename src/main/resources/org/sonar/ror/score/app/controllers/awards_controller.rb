# Copyright 2013 Catalyst IT Services
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

class AwardsController < ApplicationController
  
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  
  verify :method => :post, :only => [:create], :redirect_to => {:action => :create}
  def index
    # do not delete
  end
  
  def success
    # do not delete
  end
  
  def trophyList
   # do not delete
  end
  
  def cupList
   # do not delete
  end
  
  def create 
    @award = Award.new(params[:award])
    
    returnvalue = @award.saveTrophy()
    if ( returnvalue == Award::CRITERIA_CREATED)
      redirect_to :controller => "awards", :action => "success"
    elsif ( returnvalue == Award::CRITERIA_ALREADY_EXISTS) 
      flash[:repeatnotice] = "That criteria already exists!"
      redirect_to :controller => "awards", :action => "index"
    else
      redirect_to :controller => "awards", :action => "index"
      
    end
  
  end 
  
  def addCriteria
    respond_to do |format|        
      
      format.js   { render :js => "$('#add_criteria_id').fadeOut()" }
    end
  end
 

  
  # {Unit tests success (%);100;2w}
  
  def formatMetrics( metrics)
    
    formattedNameArray = metrics.split(',')
      
    metrics = ''
        
    for formattedName in formattedNameArray
      
      formattedName = formattedName.gsub!(';',',')
      formattedName = formattedName.sub!(/[\b{]/,'<p style="font-family:arial;font-size:11px;">&nbsp;&nbsp;&nbsp;&nbsp;')
      formattedName = formattedName.sub!(/[\b}]/,'')
      
      metrics += formattedName
         
    end   
    return metrics
   
  end
  
  # ajax callback
  def update_metrics_cup() 
    myparams = params[:selected]

    titlecupstr = myparams.gsub!(' ', '_')
    titlecupstr = 'sonar.score.TitleCup:' + titlecupstr.camelize()
    properties = Property.find(:all, :conditions => ['prop_key = ?',titlecupstr])
    for property in properties
      titlecupstrfinal = formatMetrics(property.text_value) 
    end

    render (:update) { |page| page.replace_html 'metric_div',  "<p><b>" + titlecupstrfinal + "</b></p>" }       
    
  end
  
  # ajax callback
  def update_metrics_trophy()
    myparams = params[:selected]
     
    trophystr = myparams.gsub!(' ', '_')
    trophystr = 'sonar.score.Trophy:' + trophystr.camelize()
    properties = Property.find(:all, :conditions => ['prop_key = ?',trophystr])
    for property in properties
      trophystrfinal = formatMetrics(property.text_value) 
    end
        
    render (:update) { |page| page.replace_html 'metric_div',  "<p><b>" + trophystrfinal + "</b></p>" }       
        
  end
  
  
  
end
