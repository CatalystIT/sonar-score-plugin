class TrophiesController < ApplicationController
  
  # this is an administration console
  SECTION=Navigation::SECTION_CONFIGURATION
  
  verify :method => :post, :only => [:create], :redirect_to => {:action => :create}
  def index
    # do not delete
  end
  
  def create 
    @trophy = Trophy.new(params[:trophy])
    @trophy.saveTrophy()
    redirect_to :controller => "trophies", :action => "index"          
  end 








end
