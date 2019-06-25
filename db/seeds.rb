5.times do
  article.create({
    title: Faker::Book.title,
    body: Faker::Lorem.sentence
    })
end
