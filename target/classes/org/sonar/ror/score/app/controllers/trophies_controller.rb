class TrophiesController < ApplicationController
  
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  def index
    # do not delete
  end
  
  def create 
    @trophy = Trophy.new(params[:trophy_name],params[:metric], params[:amount], params[:duration])
    @trophy.saveTrophy()
    redirect_to :controller => "trophies", :action => "index"
          
  end 

end
